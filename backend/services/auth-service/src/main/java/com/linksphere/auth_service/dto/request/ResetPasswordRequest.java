package com.linksphere.auth_service.dto.request;

import com.linksphere.common.validation.annotation.StrongPassword;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPasswordRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Token is required")
    private String otp;

    @StrongPassword(message = "Password must be strong")
    private String password;
}
