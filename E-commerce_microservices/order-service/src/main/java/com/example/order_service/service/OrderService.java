package com.example.order_service.service;


import com.example.order_service.dao.OrderDao;
import com.example.order_service.dto.*;
import com.example.order_service.exception.OrderNotFoundException;
import com.example.order_service.exception.UserNotFoundException;
import com.example.order_service.feign.CartClient;
import com.example.order_service.feign.ProductClient;
import com.example.order_service.feign.UserClient;
import com.example.order_service.mapper.EntityConverter;
import com.example.order_service.model.Order;
import com.example.order_service.model.OrderItem;
import com.example.order_service.model.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.rmi.server.LogStream.log;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderDao orderDao;

    private final UserClient userClient;

    private final CartClient cartClient;

    private final ProductClient productClient;

   private final EntityConverter<Order, OrderDto> orderConverter;

    @Transactional
    public OrderDto placeOrder(Long userId){
        UserResponse user = userClient.getUserById(userId).getBody();

        if(user == null){
            throw new UserNotFoundException("User Not Found");
        }

        Long cartId = user.getCartId();

        CartDto cartDto = cartClient.getCartById(cartId).getBody();

        if (cartDto.getCartItemDtos() == null || cartDto.getCartItemDtos().isEmpty()) {
            throw new RuntimeException("Cannot place order — cart is empty!");
        }

        Order order = new Order();
        order.setStatus(Status.PENDING);
        order.setUserId(userId);

        order.setCreatedAt(LocalDateTime.now());
        BigDecimal total = BigDecimal.ZERO;

        List<OrderItem> orderItems = new ArrayList<>();

        for(CartItemDto cartItem : cartDto.getCartItemDtos()){
            OrderItem orderItem = new OrderItem();
            ProductDto productDto = productClient.getProductById(cartItem.getProductId()).getBody();
            orderItem.setOrder(order);
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtPurchase(productDto.getPrice());
        /// total += cartItem.getProduct().getPrice() * cartItem.getQuantity();
            total = total.add(productDto.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        order.setTotalPrice(total);

        orderDao.save(order); /// Persist order + items

        userClient.addOrderToUser(userId, order.getId());

        // instead of clearing here, just call CartService
        cartClient.clearCart(cartId);

        return orderConverter.entityToDto(order, OrderDto.class);
    }

    @Transactional
    public void createOrderByUser(Long userId, Long orderId){
        userClient.addOrderToUser(userId,orderId);
    }

    public OrderDto getOrderById(Long orderId){
       Order order = orderDao.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found!"));
       return orderConverter.entityToDto(order, OrderDto.class);
    }

    public List<OrderDto> getOrdersByUser(Long userId){
        UserResponse user = userClient.getUserById(userId).getBody();

        if(user == null){
            throw new UserNotFoundException("User Not Found");
        }

         return user.getOrderIds()
                    .stream()
                    .map(orderId -> {
                        log("order ids: ").println(orderId);
                        Order order = orderDao.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found!"));
                        OrderDto orderDto = orderConverter.entityToDto(order, OrderDto.class);
                        orderDto.setUserId(userId);
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
