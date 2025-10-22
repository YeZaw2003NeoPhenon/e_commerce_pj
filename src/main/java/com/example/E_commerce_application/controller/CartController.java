package com.example.E_commerce_application.controller;

import com.example.E_commerce_application.dto.CartItemDto;
import com.example.E_commerce_application.dto.ItemToCartRequest;
import com.example.E_commerce_application.dto.RvmItemFromCartRequest;
import com.example.E_commerce_application.response.ApiResponse;
import com.example.E_commerce_application.service.CartService;
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
        return ResponseEntity.ok(ApiResponse.success(cartItems,"Cart items",null));
    }

    @PostMapping("/calculate/{userId}")
    public ResponseEntity<ApiResponse<BigDecimal>> calculateCartTotal(@PathVariable Long userId){
      BigDecimal calculateCartTotal = cartService.calculateCartTotal(userId);
      return ResponseEntity.ok(ApiResponse.success(calculateCartTotal,"calculated total results",null));
    }

}
