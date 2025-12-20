package com.react.auth.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private Long id;
    private String fullName;
}
