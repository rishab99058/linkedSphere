package com.linksphere.auth_service.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class RegisterResponse {

    private UUID id;

    private String email;

    private String message;
}