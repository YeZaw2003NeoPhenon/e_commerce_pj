package com.example.cart_service.controller;

import com.example.cart_service.dto.CartDto;
import com.example.cart_service.dto.CartItemDto;
import com.example.cart_service.dto.ItemToCartRequest;
import com.example.cart_service.dto.RvmItemFromCartRequest;
import com.example.cart_service.response.ApiResponse;
import com.example.cart_service.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add-item-cart")
    public ResponseEntity<ApiResponse<Object>> addItemToCart(@RequestBody ItemToCartRequest itemToCartRequest){
        cartService.addItemToCart(itemToCartRequest.getUserId(),itemToCartRequest.getProductId(),itemToCartRequest.getQuantity());
        return ResponseEntity.ok(ApiResponse.success("Item added to cart successfully!"));
    }

    @PostMapping("/remove-item-cart")
    public ResponseEntity<ApiResponse<Object>> removeItemFromCart(@RequestBody RvmItemFromCartRequest request){
        cartService.removeItemFromCart(request.getUserId(), request.getProductId());
        return ResponseEntity.ok(ApiResponse.success("Item removed from cart successfully!"));
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<ApiResponse<Object>> getAllCartItems(@PathVariable Long userId){
        List<CartItemDto> cartItems = cartService.getCartItems(userId);
        return ResponseEntity.ok(ApiResponse.success(cartItems,"Cart Items by userId " + userId,null));
    }

    @GetMapping("/get/{cartId}")
    public ResponseEntity<CartDto> getCartById(@PathVariable Long cartId){
        return ResponseEntity.ok(cartService.getCartById(cartId));
    }

    @PutMapping("/clear/{cartId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long cartId){
        cartService.clearCart(cartId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/calculate/{userId}")
    public ResponseEntity<ApiResponse<BigDecimal>> calculateCartTotal(@PathVariable Long userId){
      BigDecimal calculateCartTotal = cartService.calculateCartTotal(userId);
      return ResponseEntity.ok(ApiResponse.success(calculateCartTotal,"calculated total results",null));
    }

}
