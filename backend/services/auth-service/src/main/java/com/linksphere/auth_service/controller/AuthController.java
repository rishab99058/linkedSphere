package com.linksphere.auth_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.linksphere.auth_service.service.AuthService;
import com.linksphere.auth_service.dto.request.ForgotPasswordRequest;
import com.linksphere.auth_service.dto.request.GoogleLoginRequest;
import com.linksphere.auth_service.dto.request.LoginRequest;
import com.linksphere.auth_service.dto.request.RefreshTokenRequest;
import com.linksphere.auth_service.dto.request.RegisterRequest;
import com.linksphere.auth_service.dto.request.ResetPasswordRequest;
import com.linksphere.auth_service.dto.response.ForgotPasswordRespose;
import com.linksphere.auth_service.dto.response.LoginResponse;
import com.linksphere.auth_service.dto.response.RefreshTokenResponse;
import com.linksphere.auth_service.dto.response.RegisterResponse;
import com.linksphere.auth_service.dto.response.ResetPasswordResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        RegisterResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@Valid @RequestBody RefreshTokenRequest request) {
        authService.logout(request.getRefreshToken());
        return ResponseEntity.ok("User logged out successfully");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ForgotPasswordRespose> forgotPassword(
            @Valid @RequestBody ForgotPasswordRequest request) {
        return ResponseEntity.ok(authService.forgotPassword(request));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ResetPasswordResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        return ResponseEntity.ok(authService.resetPassword(request));
    }

    // social Login
    @PostMapping("/google")
    public ResponseEntity<LoginResponse> googleLogin(
            @Valid @RequestBody GoogleLoginRequest request) {
        return ResponseEntity.ok(
                authService.googleLogin(request));
    }

}
