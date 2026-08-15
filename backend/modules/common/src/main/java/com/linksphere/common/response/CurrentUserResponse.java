package com.linksphere.common.response;

import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

public record CurrentUserResponse(
        @JsonProperty("authId") UUID authId,
        String email,
        String phoneNumber,
        Set<String> roles) {
}
