package com.linksphere.auth_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.linksphere.auth_service.security.user.CustomUserDetails;
import com.linksphere.common.response.CurrentUserResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {

    @GetMapping("/me")
    public ResponseEntity<CurrentUserResponse> getCurrentUser(
            Authentication authentication) {

        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();

        CurrentUserResponse response = new CurrentUserResponse(
                principal.getUser().getId(),
                principal.getUser().getEmail(),
                principal.getUser().getPhoneNumber(),
                principal.getUser().getUserRoles().stream()
                        .map(role -> role.getRole().getName())
                        .collect(java.util.stream.Collectors.toSet()));

        return ResponseEntity.ok(response);
    }

}
