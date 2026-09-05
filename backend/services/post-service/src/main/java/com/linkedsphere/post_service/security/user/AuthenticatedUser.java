package com.linkedsphere.post_service.security.user;

import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthenticatedUser {

    private final UUID userId;
    private final String email;
}