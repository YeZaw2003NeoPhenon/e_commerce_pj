package com.example.app_user_service.controller;

import com.example.app_user_service.dto.UserRequest;
import com.example.app_user_service.dto.UserResponse;
import com.example.app_user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.registerUser(userRequest));
    }

    @PostMapping("/add-user-order")
    public ResponseEntity<Void> addOrderToUser(@RequestParam Long userId,
                                               @RequestParam Long orderId){
        userService.addOrderToUser(userId,orderId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add/user-cart")
    public ResponseEntity<Object> setCartUser(@RequestParam Long userId,
                @RequestParam Long cartId){
            userService.setCartUser(userId, cartId);
            return ResponseEntity.created(URI.create("/api/v1/categories/create")).build();
        }

        @GetMapping("/get/user-orders/{userId}")
        public ResponseEntity<List<Long>> getUsersOrderIds (@PathVariable Long userId){
            return ResponseEntity.ok(userService.getUserOrderIds(userId));
        }

        @GetMapping("/get/user-cart/{userId}")
        public ResponseEntity<Long> getUserCartId(@PathVariable Long userId){
            return ResponseEntity.ok(userService.getUserCartId(userId));
        }

}
