package com.example.app_user_service.dto;

import com.example.app_user_service.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private Long id;

    private String name;
    private String email;
    private String password;

    private Role role;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public UserRequest(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

}
