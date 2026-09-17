package com.linkedsphere.notification_service.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.linkedsphere.notification_service.dto.request.PushNotificationRequest;
import com.linkedsphere.notification_service.service.PushNotificationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/push")
@RequiredArgsConstructor
public class PushNotificationController {

    private final PushNotificationService pushNotificationService;

    @PostMapping("/send")
    public ResponseEntity<Map<String, Object>> sendPushNotification(
            @Valid @RequestBody PushNotificationRequest request) {
        String messageId = pushNotificationService.sendPushNotification(request);
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Push notification sent successfully",
                "messageId", messageId
        ));
    }

    @GetMapping("/test-dummy")
    public ResponseEntity<Map<String, Object>> testDummyNotification(
            @RequestParam("token") String token) {
        String messageId = pushNotificationService.sendDummyNotification(token);
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Dummy push notification sent successfully",
                "messageId", messageId,
                "targetToken", token
        ));
    }
}
