package com.example.product_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductWithCategoryResponse {

    private String name;

    private String description;

    private BigDecimal price;

    private Integer stockQuantity;

    private String categoryName;

    private String categoryDescription;
}
