package com.example.E_commerce_application.service;

import com.example.E_commerce_application.dao.CartDao;
import com.example.E_commerce_application.dao.OrderDao;
import com.example.E_commerce_application.dao.UserDao;
import com.example.E_commerce_application.dto.CartDto;
import com.example.E_commerce_application.dto.OrderDto;
import com.example.E_commerce_application.dto.UserDto;
import com.example.E_commerce_application.exception.OrderNotFoundException;
import com.example.E_commerce_application.exception.UserNotFoundException;
import com.example.E_commerce_application.mapper.EntityConverter;
import com.example.E_commerce_application.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderDao orderDao;
    private final CartDao cartDao;

    private final UserDao userDao;

    private final EntityConverter<Order, OrderDto> orderConverter;

    private final EntityConverter<User, UserDto> userConverter;

    private final EntityConverter<Cart, CartDto> cartConverter;


    @Transactional
    public OrderDto placeOrder(Long userId){
        User user = userDao.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found!"));

        Cart cart = user.getCart();

        if(cart.getCartItems().isEmpty()){
            throw new RuntimeException("Cannot place order — cart is empty!");
        }

        Order order = new Order();
        order.setStatus(Status.PENDING);
        order.setUser(user);

        order.setCreatedAt(LocalDateTime.now());
        BigDecimal total = BigDecimal.ZERO;

        List<OrderItem> orderItems = new ArrayList<>();

        for(CartItem cartItem : cart.getCartItems()){
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtPurchase(cartItem.getProduct().getPrice());
        /// total += cartItem.getProduct().getPrice() * cartItem.getQuantity();
            total = total.add(cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        order.setTotalPrice(total);

        orderDao.save(order); /// Persist order + items
        cart.getCartItems().clear();
        cartDao.save(cart);
        return orderConverter.entityToDto(order, OrderDto.class);
    }

    public OrderDto getOrderById(Long orderId){
       Order order = orderDao.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found!"));
       return orderConverter.entityToDto(order, OrderDto.class);
    }

    public List<OrderDto> getOrdersByUser(Long userId){
        User user = userDao.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found!"));
        UserDto userDto = userConverter.entityToDto(user, UserDto.class);
        CartDto cartDto = cartConverter.entityToDto(user.getCart(), CartDto.class);
        userDto.setCartDto(cartDto);

         return user.getOrders()
                    .stream()
                    .map(order -> {
                        OrderDto orderDto = orderConverter.entityToDto(order, OrderDto.class);
                        orderDto.setUserDto(userDto);
                        return orderDto;
                    })
                    .collect(Collectors.toList());
    }

    public void cancelOrder(Long orderId){
        Order order = orderDao.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found!"));
        order.setStatus(Status.CANCELLED);
        orderDao.save(order);
    }

    public void deleteOrderById(Long orderId){
        Order order = orderDao.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found!"));
        orderDao.delete(order);
    }

}
