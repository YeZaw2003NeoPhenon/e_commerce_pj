package com.example.E_commerce_application.dto;
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
    private CartDto cartDto;
    private ProductDto productDto;

    private int quantity;

    private BigDecimal priceSnapshot;

}
