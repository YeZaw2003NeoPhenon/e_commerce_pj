package com.example.E_commerce_application.service;

import com.example.E_commerce_application.dao.CartDao;
import com.example.E_commerce_application.dao.CartItemDao;
import com.example.E_commerce_application.dao.ProductDao;
import com.example.E_commerce_application.dao.UserDao;
import com.example.E_commerce_application.dto.CartItemDto;
import com.example.E_commerce_application.dto.ProductDto;
import com.example.E_commerce_application.exception.ProductNotFoundException;
import com.example.E_commerce_application.exception.UserNotFoundException;
import com.example.E_commerce_application.mapper.EntityConverter;
import com.example.E_commerce_application.model.Cart;
import com.example.E_commerce_application.model.CartItem;
import com.example.E_commerce_application.model.Product;
import com.example.E_commerce_application.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartDao cartDao;
    private final ProductDao productDao;
    private final UserDao userDao;
    private final CartItemDao cartItemDao;

    private final EntityConverter<CartItem, CartItemDto> cartItemConverter;

    private final EntityConverter<Product, ProductDto> productConverter;


    @Transactional
    public void addItemToCart(Long userId, Long productId, int quantity) {

        User user = userDao.findById(userId)
                                 .orElseThrow(() -> new UserNotFoundException("User not found!"));

        Product product = productDao.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found!"));

        Cart cart = user.getCart();

        if(cart == null){
            cart = new Cart(user);
            cartDao.save(cart);
            user.setCart(cart);
        }

        Optional<CartItem> existingItem = cart.getCartItems()
                                              .stream()
                                              .filter(item -> item.getProduct().getId().equals(productId))
                                              .findAny();

       if(existingItem.isPresent()){
           CartItem cartItem = existingItem.get();
           cartItem.setQuantity(quantity + cartItem.getQuantity());
           cartItemDao.save(cartItem);
       }
        else{
            CartItem cartItem = new CartItem(cart,product,quantity,product.getPrice());
            cartItemDao.save(cartItem);
       }

    }

    @Transactional
    public void removeItemFromCart(Long userId, Long productId){
        User user = userDao.findById(userId)
                                 .orElseThrow(() -> new UserNotFoundException("User not found!"));

        Cart cart = user.getCart();

        if(cart == null){
            throw new RuntimeException("Cart not found");
        }

        CartItem cartItem = cartItemDao.findByCartIdAndProductId(cart.getId(), productId);

        cartItemDao.delete(cartItem);
    }

    public void clearCart(Long userId){
        User user = userDao.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found!"));

        Cart cart = user.getCart();

        if(cart == null){
            throw new RuntimeException("Cart not found");
        }
        cartItemDao.deleteAll(cart.getCartItems());
    }

    public List<CartItemDto> getCartItems(Long userId){
        User user = userDao.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found!"));
        Cart cart = user.getCart();
        if(cart == null){
            throw new RuntimeException("Cart not found");
        }
         return cart.getCartItems()
                    .stream()
                    .map(cartItem -> {
                      CartItemDto cartItemDto = cartItemConverter.entityToDto(cartItem, CartItemDto.class);
                      Product product = cartItem.getProduct();
                      if(product != null){
                         ProductDto productDto = productConverter.entityToDto(product, ProductDto.class);
                         cartItemDto.setProductDto(productDto);
                      }
                      return cartItemDto;
                    })
                    .collect(Collectors.toList());
    }

    public BigDecimal calculateCartTotal(Long userId){
       List<CartItemDto> cartItems = getCartItems(userId);

       return cartItems.stream()
               .map(cartItemDto -> cartItemConverter.dtoToEntity(cartItemDto, CartItem.class))
               .map(item -> item.getPriceSnapshot().multiply(BigDecimal.valueOf(item.getQuantity())))
               .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}