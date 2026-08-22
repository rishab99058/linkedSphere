package com.linkedsphere.notification_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.linkedsphere.notification_service.service.EmailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class EmailCotroller {

    private final EmailService emailService;

    @GetMapping("/test-password-reset")
    public String testPasswordReset(
            @RequestParam("email") String email) {

        emailService.sendPasswordResetOtp(email, "123456");

        return "Password reset email sent to " + email;
    }

}
