package com.example.cart_service.service;

import com.example.cart_service.dao.CartDao;
import com.example.cart_service.dao.CartItemDao;
import com.example.cart_service.dto.CartDto;
import com.example.cart_service.dto.CartItemDto;
import com.example.cart_service.dto.ProductDto;
import com.example.cart_service.dto.UserResponse;
import com.example.cart_service.exception.CartNotFoundException;
import com.example.cart_service.exception.ProductNotFoundException;
import com.example.cart_service.exception.UserNotFoundException;
import com.example.cart_service.feign.ProductClient;
import com.example.cart_service.feign.UserClient;
import com.example.cart_service.mapper.EntityConverter;
import com.example.cart_service.model.Cart;
import com.example.cart_service.model.CartItem;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartDao cartDao;
    private final UserClient userClient;
    private final ProductClient productClient;

    private final CartItemDao cartItemDao;

    private final EntityConverter<Cart, CartDto> cartConverter;

    private final EntityConverter<CartItem, CartItemDto> cartItemConverter;


    @Transactional
    public void addItemToCart(Long userId, Long productId, int quantity) {

        UserResponse user = userClient.getUserById(userId).getBody();

        ProductDto productDto = productClient.getProductById(productId).getBody();

        if (user == null) {
            throw new UserNotFoundException("User not found!");
        }
        if (productDto == null) {
            throw new ProductNotFoundException("Product not found!");
        }

        Long cartId = user.getCartId();

        Cart cart;
        if(cartId == null){
            cart = new Cart();
            cart.setUserId(userId);
            cartDao.save(cart);
            userClient.setCartUser(userId, cart.getId());
        }
        else{
             cart = cartDao.findById(cartId).orElseThrow(() -> new CartNotFoundException("Cart not found"));
        }

        Optional<CartItem> existingItem = cart.getCartItems()
                                              .stream()
                                              .filter(item -> item.getProductId().equals(productId))
                                              .findAny();

       if(existingItem.isPresent()){
           CartItem cartItem = existingItem.get();
           cartItem.setQuantity(quantity + cartItem.getQuantity());
           cartItemDao.save(cartItem);
       }
        else{
            CartItem cartItem = new CartItem(cart,productId,quantity,productDto.getPrice());
            cartItemDao.save(cartItem);
       }
    }

    public CartDto getCartById(Long cartId){
        return cartDao.findById(cartId).map(
                cart -> {
                    CartDto cartDto = cartConverter.entityToDto(cart, CartDto.class);
                    if(cart.getCartItems() != null){
                        List<CartItemDto> cartItemDtos = cart.getCartItems()
                                                            .stream()
                                                            .map(cartItem -> cartItemConverter.entityToDto(cartItem, CartItemDto.class))
                                                            .collect(Collectors.toList());

                        cartDto.setCartItemDtos(cartItemDtos);
                    }
                    return cartDto;
                }
        ).orElseThrow(() -> new CartNotFoundException("Cart Not Found"));
    }

    @Transactional
    public void removeItemFromCart(Long userId, Long productId){

        UserResponse user = userClient.getUserById(userId).getBody();

        if(user == null){
            throw new UserNotFoundException("User not found!");
        }

        Long cartId = user.getCartId();

        if(cartId == null){
            throw new CartNotFoundException("Cart not found");
        }

        Cart cart = cartDao.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));

        CartItem cartItem = cartItemDao.findByCartIdAndProductId(cart.getId(), productId);

        if(cartItem != null){
            cartItemDao.delete(cartItem);
        }
    }

    @Transactional
    public void clearCart(Long cartId){

        if(cartId == null){
            throw new CartNotFoundException("Cart not found");
        }

        Cart cart = cartDao.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));

     //   cartItemDao.deleteAll(cart.getCartItems());
        cart.getCartItems().clear();
        cartDao.save(cart);
    }

    public List<CartItemDto> getCartItems(Long userId){

        UserResponse user = userClient.getUserById(userId).getBody();

        if(user == null){
            throw new UserNotFoundException("User not found!");
        }

        Long cartId = user.getCartId();

        if(cartId == null){
            Cart newCart = new Cart();
            newCart.setUserId(userId);
            cartDao.save(newCart);
            userClient.setCartUser(userId, newCart.getId());
            cartId = newCart.getId();
        }

        Cart cart = cartDao.findById(cartId)
                           .orElseThrow(() -> new CartNotFoundException("Cart not found"));

        return cart.getCartItems()
                    .stream()
                    .map(cartItem -> {
                        CartItemDto cartItemDto = cartItemConverter.entityToDto(cartItem, CartItemDto.class);

                      if(cartItem.getCart() != null){
                            CartDto cartDto = cartConverter.entityToDto(cartItem.getCart(), CartDto.class);
                            cartItemDto.setCartDto(cartDto);
                      }

                      Long productId = cartItem.getProductId();
                      cartItemDto.setProductId(productId);
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