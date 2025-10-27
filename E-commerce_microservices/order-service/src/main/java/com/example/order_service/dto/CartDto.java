package com.example.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private Long id;

    private List<CartItemDto> cartItemDtos = new ArrayList<>();

    private Long userId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public CartDto(Long userId) {
        this.userId = userId;
    }

}
