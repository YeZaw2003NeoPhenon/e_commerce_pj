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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@Transactional
class CartServiceTest {

    @Mock
    private CartDao cartDao;

    @Mock
    private ProductDao productDao;

    @Mock
    private UserDao userDao;

    @Mock
    private CartItemDao cartItemDao;

    @Mock
    private EntityConverter<CartItem, CartItemDto> cartItemConverter;

    @Mock
    private EntityConverter<Product, ProductDto> productConverter;

    @InjectMocks
    private CartService cartService;


    @Test
    void testAddItemToCart_IfItemExists(){
        Long userId = 1L;
        Long productId = 10L;

        User user = new User();
        user.setId(userId);

        Cart cart = new Cart();
        cart.setId(productId);
        cart.setCartItems(new ArrayList<>());

        Product product = new Product();
        product.setId(productId);
        product.setPrice(new BigDecimal("100.0"));

        CartItem existingItem = new CartItem(cart, product, 3, product.getPrice());
        cart.getCartItems().add(existingItem);

        user.setCart(cart);
        when(userDao.findById(userId)).thenReturn(Optional.of(user));
        when(productDao.findById(productId)).thenReturn(Optional.of(product));

        cartService.addItemToCart(userId,productId,2);

        verify(cartItemDao, times(1)).save(existingItem);
        assertThat(existingItem.getQuantity()).isEqualTo(5);

    }

    @Test
    void addItemToCart_ShouldThrowUserNotFound() {
        when(userDao.findById(999L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> {
            cartService.addItemToCart(999L, 10L, 1);
        });
    }

    @Test
    void addItemToCart_ShouldThrowProductNotFound() {
        User user = new User();
        Long userId = 1L;
        user.setId(userId);

        when(userDao.findById(userId)).thenReturn(Optional.of(user));
        when(productDao.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            cartService.addItemToCart(userId, 999L, 1);
        });
    }

    @Test
    void testRemoveItemFromCart(){
        Long userId = 1L;
        Long productId = 10L;

        User user = new User();
        user.setId(userId);

        Cart cart = new Cart();
        Long cartId = 2L;
        cart.setId(cartId);
        cart.setCartItems(new ArrayList<>());

        Product product = new Product();
        product.setId(productId);
        product.setPrice(new BigDecimal("100.0"));

        CartItem cartItem = new CartItem(cart, product, 3, product.getPrice());
        user.setCart(cart);

        when(userDao.findById(userId)).thenReturn(Optional.of(user));
        when(cartItemDao.findByCartIdAndProductId(cartId,productId)).thenReturn(cartItem);

        cartService.removeItemFromCart(userId,productId);

        verify(cartItemDao, times(1)).delete(cartItem);
    }

    @Test
    void testGetCartItems() {
        Long userId = 1L;

        User user = new User();
        user.setId(userId);

        Cart cart = new Cart();
        cart.setCartItems(new ArrayList<>());
        user.setCart(cart);

        Product product = new Product();
        product.setId(10L);
        product.setPrice(new BigDecimal("100.0"));

        CartItem cartItem = new CartItem(cart, product, 2, new BigDecimal("100.00"));
        cart.getCartItems().add(cartItem);

        when(userDao.findById(userId)).thenReturn(Optional.of(user));

        CartItemDto cartItemDto = new CartItemDto();
        when(cartItemConverter.entityToDto(cartItem, CartItemDto.class))
                .thenReturn(cartItemDto);

        ProductDto productDto = new ProductDto();
        when(productConverter.entityToDto(product, ProductDto.class)).thenReturn(productDto);

        List<CartItemDto> result = cartService.getCartItems(userId);

        assertEquals(1, result.size());
        assertThat(result.get(0)).isEqualTo(cartItemDto);
        assertThat(result.get(0).getProductDto()).isEqualTo(productDto);
    }

}