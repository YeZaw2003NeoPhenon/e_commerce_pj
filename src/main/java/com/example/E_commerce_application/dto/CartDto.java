package com.example.E_commerce_application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private Long id;

    private List<CartItemDto> cartItemDtos;

    private UserDto userDto;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public CartDto(UserDto userDto) {
        this.userDto = userDto;
    }

}
