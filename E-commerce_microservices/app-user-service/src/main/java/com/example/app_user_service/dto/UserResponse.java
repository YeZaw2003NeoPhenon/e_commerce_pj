package com.example.app_user_service.dto;

import com.example.app_user_service.model.Role;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;

    private String name;

    private String email;

    private String password;

    private Role role;

    private List<Long> orderIds;

    private Long cartId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
