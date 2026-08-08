package com.linksphere.auth_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {

        return ResponseEntity.ok(authentication.getPrincipal());
    }

}
