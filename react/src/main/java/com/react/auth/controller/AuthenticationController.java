package com.react.auth.controller;

import com.react.auth.dto.*;
import com.react.auth.service.AuthenticationService;
import com.react.auth.service.JwtService;
import com.react.auth.service.RefreshTokenService;
import com.react.model.user.UserRole;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh(
            @RequestBody RefreshRequest req
    ) {

        var rotated = refreshTokenService.rotate(req.getRefreshToken());

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(
                        rotated.getUserId().toString()
                );

        String access = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(
                new AuthenticationResponse(
                        access,
                        UserRole.STUDENT,
                        rotated.getToken()
                )
        );
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(
            @RequestBody LogoutRequest request
    ) {
        refreshTokenService.revokeByToken(request.getRefreshToken());
        return ResponseEntity.ok().build();
    }
}
