package com.example.E_commerce_application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RvmItemFromCartRequest {
    private Long userId;
    private Long productId;
}
