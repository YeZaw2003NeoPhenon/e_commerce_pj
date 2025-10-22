package com.example.E_commerce_application.controller;

import com.example.E_commerce_application.dto.LoginRequest;
import com.example.E_commerce_application.dto.UserRequest;
import com.example.E_commerce_application.dto.UserResponse;
import com.example.E_commerce_application.response.ApiResponse;
import com.example.E_commerce_application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long userId){
        return ResponseEntity.ok(ApiResponse.success(userService.getUserById(userId), "Tracked User", null));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> registerUser(@RequestBody UserRequest userRequest){
        return ResponseEntity.ok(ApiResponse.success(userService.registerUser(userRequest), "User is created successfully!",null));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(ApiResponse.success(userService.login(loginRequest), "User login successfully!",null));
    }

}
