package com.auth.payload

import com.entity.RefreshToken
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor

data class AuthenticationResponse(
    var token: String? = null,
    var role: String? = null,
    var refreshToken: String? = null,
)