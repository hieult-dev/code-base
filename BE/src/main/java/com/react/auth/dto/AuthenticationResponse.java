package com.react.auth.dto;

import com.react.user.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    private String token;
    private UserRole role;
    private String refreshToken;
}
