package com.example.order_service.controller;

import com.example.order_service.dto.OrderDto;
import com.example.order_service.response.ApiResponse;
import com.example.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/place-order/{userId}")
    public ResponseEntity<ApiResponse<OrderDto>> addOrderFromCart(@PathVariable Long userId){
        return ResponseEntity.created(URI.create("/api/v1/orders/place-order/" + userId))
                             .body(ApiResponse.success(orderService.placeOrder(userId), "You placed order successfully!", null));
    }

    @PostMapping("/add-order-user")
    public ResponseEntity<ApiResponse<Void>> addOrderToUser(@RequestParam Long userId, @RequestParam Long orderId){
        orderService.createOrderByUser(userId,orderId);
        return ResponseEntity.ok(ApiResponse.success("Order is added to user successfully!"));
    }

    @GetMapping("/get/{orderId}")
    public ResponseEntity<ApiResponse<OrderDto>> getOrderById(@PathVariable Long orderId){
        return ResponseEntity.ok(ApiResponse.success(orderService.getOrderById(orderId), "Tracked Order", null));
    }

    @GetMapping("/get-user-order/{userId}")
    public ResponseEntity<ApiResponse<List<OrderDto>>> getOrderByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(ApiResponse.success(orderService.getOrdersByUser(userId), "Tracked Orders By userId: " + userId, null));
    }

    @PostMapping("/cancel/{orderId}")
    public ResponseEntity<Object> cancelOrder(@PathVariable Long orderId){
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok(ApiResponse.success("Order is canceled"));
    }

    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<Object> deleteOrder(@PathVariable Long orderId){
        orderService.deleteOrderById(orderId);
        return ResponseEntity.ok(ApiResponse.success("Order is deleted"));
    }

}
