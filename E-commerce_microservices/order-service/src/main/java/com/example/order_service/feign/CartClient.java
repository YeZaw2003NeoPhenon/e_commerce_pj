package com.example.order_service.feign;

import com.example.order_service.dto.CartDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient("CART-SERVICE")
public interface CartClient {

    @GetMapping("/api/v1/carts/get/{cartId}")
    ResponseEntity<CartDto> getCartById(@PathVariable Long cartId);

    @PutMapping("/api/v1/carts/clear/{cartId}}")
    ResponseEntity<Void> clearCart(@PathVariable Long cartId);
}
