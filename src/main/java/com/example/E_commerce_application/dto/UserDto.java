package com.example.E_commerce_application.dto;

import com.example.E_commerce_application.model.Role;
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
public class UserDto {

    private int id;

    private String name;
    private String email;

    private Role role;

    private CartDto cartDto;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public UserDto(String name, String email, Role role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }
}
