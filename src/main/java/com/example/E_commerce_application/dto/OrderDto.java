package com.example.E_commerce_application.dto;

import com.example.E_commerce_application.model.OrderItem;
import com.example.E_commerce_application.model.Status;
import com.example.E_commerce_application.model.User;
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

    private UserDto userDto;

    private BigDecimal totalPrice;

    private Status status;

    private LocalDateTime createdAt;

    private List<OrderItem> orderItems;

    public OrderDto(UserDto userDto, BigDecimal totalPrice, Status status) {
        this.userDto = userDto;
        this.totalPrice = totalPrice;
        this.status = status;
    }
}
