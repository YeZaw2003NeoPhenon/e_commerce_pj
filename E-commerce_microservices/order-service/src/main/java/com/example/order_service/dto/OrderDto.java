package com.example.order_service.dto;

import com.example.order_service.model.OrderItem;
import com.example.order_service.model.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long userId;

    private BigDecimal totalPrice;

    private Status status;

    private LocalDateTime createdAt;

    private List<OrderItem> orderItems;

    public OrderDto(Long userId, BigDecimal totalPrice, Status status) {
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.status = status;
    }

}
