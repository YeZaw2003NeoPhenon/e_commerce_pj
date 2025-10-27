package com.example.cart_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {

    private Long id;
    private CartDto cartDto;

    private Long productId;

    private int quantity;

    private BigDecimal priceSnapshot;

    public CartItemDto(CartDto cartDto, Long productId, int quantity, BigDecimal priceSnapshot) {
        this.cartDto = cartDto;
        this.productId = productId;
        this.quantity = quantity;
        this.priceSnapshot = priceSnapshot;
    }
}
