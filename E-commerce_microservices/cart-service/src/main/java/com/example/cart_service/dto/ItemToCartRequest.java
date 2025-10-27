package com.example.cart_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemToCartRequest {
    private Long userId;
    private Long productId;
    private int quantity;
}
