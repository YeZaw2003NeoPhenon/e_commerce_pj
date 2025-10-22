package com.example.E_commerce_application.service;

import com.example.E_commerce_application.dao.CartDao;
import com.example.E_commerce_application.dao.OrderDao;
import com.example.E_commerce_application.dao.UserDao;
import com.example.E_commerce_application.dto.CartDto;
import com.example.E_commerce_application.dto.OrderDto;
import com.example.E_commerce_application.dto.UserDto;
import com.example.E_commerce_application.mapper.EntityConverter;
import com.example.E_commerce_application.model.*;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@Transactional
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderDao orderDao;

    @Mock
    private CartDao cartDao;

    @Mock
    private UserDao userDao;

    @Mock
    private EntityConverter<Order, OrderDto> orderConverter;

    @Mock
    private EntityConverter<User, UserDto> userConverter;

    @Mock
    private EntityConverter<Cart, CartDto> cartConverter;

    @InjectMocks
    private OrderService orderService;

    private User user;

    private UserDto userDto;

    private Cart cart;

    private CartDto cartDto;

    private Order order;

    private OrderDto orderDto;

    @BeforeEach
    void setUp(){
        user = new User("Neo","neo@gmail.com", Role.ADMIN);
        userDto = new UserDto("Neo","neo@gmail.com", Role.ADMIN);
        cart = new Cart(new ArrayList<>(), user);
        cartDto = new CartDto(userDto);
        order = new Order(user, new BigDecimal("500"), Status.PENDING);
        orderDto = new OrderDto(userDto,new BigDecimal("500"), Status.PENDING);
    }

    @Test
    void testGetOrdersByUser(){
        user.setId(1L);
        user.setOrders(List.of(order));
        order.setUser(user);

        when(userDao.findById(1L)).thenReturn(Optional.of(user));
        when(userConverter.entityToDto(any(User.class), eq(UserDto.class))).thenReturn(userDto);
        when(cartConverter.entityToDto(any(Cart.class), eq(CartDto.class))).thenReturn(cartDto);
        when(orderConverter.entityToDto(any(Order.class), eq(OrderDto.class))).thenReturn(orderDto);

        List<OrderDto> trackedOrders = orderService.getOrdersByUser(1L);

        assertThat(trackedOrders.get(0).getUserDto().getName()).isEqualTo("Neo");
    }


}