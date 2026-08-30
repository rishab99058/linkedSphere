package com.linksphere.common.enums;

import lombok.Getter;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

        // ===========================
        // Common
        // ===========================

        INTERNAL_SERVER_ERROR(
                        "COMMON_001",
                        "Internal server error",
                        HttpStatus.INTERNAL_SERVER_ERROR),

        VALIDATION_FAILED(
                        "COMMON_002",
                        "Validation failed",
                        HttpStatus.BAD_REQUEST),

        RESOURCE_NOT_FOUND(
                        "COMMON_003",
                        "Resource not found",
                        HttpStatus.NOT_FOUND),

        ACCESS_DENIED(
                        "COMMON_004",
                        "Access Denied",
                        HttpStatus.FORBIDDEN),

        // ===========================
        // Authentication
        // ===========================

        EMAIL_ALREADY_EXISTS(
                        "AUTH_001",
                        "Email already exists",
                        HttpStatus.CONFLICT),

        PHONE_ALREADY_EXISTS(
                        "AUTH_002",
                        "Phone number already exists",
                        HttpStatus.CONFLICT),

        INVALID_CREDENTIALS(
                        "AUTH_003",
                        "Invalid email or password",
                        HttpStatus.UNAUTHORIZED),

        ACCOUNT_DISABLED(
                        "AUTH_004",
                        "Account is disabled",
                        HttpStatus.FORBIDDEN),

        ACCOUNT_LOCKED(
                        "AUTH_005",
                        "Account is locked",
                        HttpStatus.FORBIDDEN),

        ROLE_NOT_FOUND(
                        "AUTH_006",
                        "Role not found",
                        HttpStatus.FORBIDDEN),
        RESOURCE_EXPIRED(
                        "HTTP_400",
                        "Resource expired",
                        HttpStatus.BAD_REQUEST),

        PROFILE_NOT_FOUND(
                        "PROFILE_NOT_FOUND",
                        "Profile has not been set up",
                        HttpStatus.NOT_FOUND);

        private final String code;

        private final String message;

        private final HttpStatus httpStatus;

        ErrorCode(
                        String code,
                        String message,
                        HttpStatus httpStatus) {
                this.code = code;
                this.message = message;
                this.httpStatus = httpStatus;
        }

}