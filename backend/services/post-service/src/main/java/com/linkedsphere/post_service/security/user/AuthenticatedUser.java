package com.linkedsphere.post_service.security.user;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticatedUser {

    private UUID userId;
    private String email;
    @Builder.Default
    private List<String> roles = List.of();

    public AuthenticatedUser(UUID userId, String email) {
        this.userId = userId;
        this.email = email;
        this.roles = List.of();
    }
}