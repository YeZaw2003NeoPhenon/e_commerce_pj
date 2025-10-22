package com.example.E_commerce_application.dto;

import com.example.E_commerce_application.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;

    private String name;
    private String email;

    private Role role;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public UserResponse(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

}
