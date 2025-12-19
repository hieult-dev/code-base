package com.react.dto.user;

import com.react.model.user.UserRole;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private Long id;
    private String fullName;
    private String email;
    private UserRole role = UserRole.STUDENT;
    private LocalDateTime createdAt = LocalDateTime.now();
}
