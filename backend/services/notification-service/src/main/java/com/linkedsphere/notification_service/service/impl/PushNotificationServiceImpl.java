package com.linkedsphere.notification_service.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.linkedsphere.notification_service.dto.request.PushNotificationRequest;
import com.linkedsphere.notification_service.service.PushNotificationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PushNotificationServiceImpl implements PushNotificationService {

    private final FirebaseMessaging firebaseMessaging;

    @Override
    public String sendPushNotification(PushNotificationRequest request) {
        if ((request.getToken() == null || request.getToken().isBlank())
                && (request.getTopic() == null || request.getTopic().isBlank())) {
            throw new IllegalArgumentException("Either token or topic must be provided for push notification");
        }

        Notification.Builder notificationBuilder = Notification.builder()
                .setTitle(request.getTitle())
                .setBody(request.getBody());

        if (request.getImageUrl() != null && !request.getImageUrl().isBlank()) {
            notificationBuilder.setImage(request.getImageUrl());
        }

        Message.Builder messageBuilder = Message.builder()
                .setNotification(notificationBuilder.build());

        if (request.getToken() != null && !request.getToken().isBlank()) {
            messageBuilder.setToken(request.getToken().trim());
        } else {
            messageBuilder.setTopic(request.getTopic().trim());
        }

        if (request.getData() != null && !request.getData().isEmpty()) {
            messageBuilder.putAllData(request.getData());
        }

        Message message = messageBuilder.build();

        try {
            String messageId = firebaseMessaging.send(message);
            log.info("FCM push notification sent successfully, messageId: {}", messageId);
            return messageId;
        } catch (FirebaseMessagingException e) {
            log.error("Failed to send FCM push notification: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to send FCM push notification: " + e.getMessage(), e);
        }
    }

    @Override
    public String sendDirectNotification(String token, String title, String body, Map<String, String> data) {
        PushNotificationRequest request = PushNotificationRequest.builder()
                .token(token)
                .title(title)
                .body(body)
                .data(data)
                .build();
        return sendPushNotification(request);
    }

    @Override
    public String sendTopicNotification(String topic, String title, String body, Map<String, String> data) {
        PushNotificationRequest request = PushNotificationRequest.builder()
                .topic(topic)
                .title(title)
                .body(body)
                .data(data)
                .build();
        return sendPushNotification(request);
    }

    @Override
    public String sendDummyNotification(String token) {
        log.info("Sending dummy push notification to token: {}", token);
        PushNotificationRequest dummyRequest = PushNotificationRequest.builder()
                .token(token)
                .title("Welcome to LinkedSphere! 🚀")
                .body("This is a test notification from LinkedSphere dev environment.")
                .data(Map.of(
                        "type", "TEST_NOTIFICATION",
                        "timestamp", String.valueOf(System.currentTimeMillis())
                ))
                .build();
        return sendPushNotification(dummyRequest);
    }
}
