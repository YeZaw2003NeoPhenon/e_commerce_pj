package com.example.cart_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RvmItemFromCartRequest {
    private Long userId;
    private Long productId;
}
