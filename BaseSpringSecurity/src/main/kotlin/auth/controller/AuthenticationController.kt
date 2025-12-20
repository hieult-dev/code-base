package com.auth.controller

import com.auth.payload.AuthenticationRequest
import com.auth.payload.AuthenticationResponse
import com.auth.service.AuthenticationService
import com.auth.payload.RegisterRequest
import com.auth.service.RefreshTokenService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.auth.dto.RefreshRequest
import com.auth.service.JwtService
import org.springframework.security.core.userdetails.UserDetailsService


@RestController
@RequestMapping("/api/auth")
open class AuthenticationController(
    private val authenticationService: AuthenticationService,
    private val refreshTokenService: RefreshTokenService,
    private val jwtService: JwtService,
    private val userDetailService: UserDetailsService
) {

    @PostMapping("/register")
    fun register(
        @RequestBody request: RegisterRequest
    ): ResponseEntity<AuthenticationResponse> {
        return ResponseEntity.ok(authenticationService.register(request))
    }

    @PostMapping("/authenticate")
    open fun authenticate(
        @Valid
        @RequestBody request: AuthenticationRequest,
    ): ResponseEntity<AuthenticationResponse> {
        println("vào được controller")
        return ResponseEntity.ok(authenticationService.authenticate(request))
    }

    @PostMapping("/refresh")
    open fun refresh(@RequestBody req: RefreshRequest): ResponseEntity<AuthenticationResponse> {
        println("refresh")
        val rotated = refreshTokenService.rotate(req.refreshToken)
        val userDetails = userDetailService.loadUserByUsername(rotated.userId.toString())
        val access = jwtService.generateToken(userDetails)
        return ResponseEntity.ok(AuthenticationResponse(access,
            "STUDENT",
            rotated.token))
    }

}