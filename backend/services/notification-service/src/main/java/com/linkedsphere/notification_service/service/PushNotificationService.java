package com.linkedsphere.notification_service.service;

import java.util.Map;

import com.linkedsphere.notification_service.dto.request.PushNotificationRequest;

public interface PushNotificationService {

    String sendPushNotification(PushNotificationRequest request);

    String sendDirectNotification(String token, String title, String body, Map<String, String> data);

    String sendTopicNotification(String topic, String title, String body, Map<String, String> data);

    String sendDummyNotification(String token);
}
