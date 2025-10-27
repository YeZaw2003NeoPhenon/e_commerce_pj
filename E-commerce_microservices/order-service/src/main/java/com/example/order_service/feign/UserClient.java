package com.example.order_service.feign;

import com.example.order_service.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("APP-USER-SERVICE")
public interface UserClient {

    @GetMapping("/api/v1/users/get/{userId}")
    ResponseEntity<UserResponse> getUserById(@PathVariable Long userId);

    @PostMapping("/api/v1/users/add/user-cart")
    ResponseEntity<Object> setCartUser(@RequestParam Long userId,
                                       @RequestParam Long cartId);

    @GetMapping("/api/v1/users/get/user-cart/{userId}")
    ResponseEntity<Long> getUserCartId(@PathVariable Long userId);

    @PostMapping("/api/v1/users/add-user-order")
    ResponseEntity<Void> addOrderToUser(@RequestParam Long userId, @RequestParam Long orderId);
}
