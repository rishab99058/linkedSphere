package com.linkedsphere.notification_service.consumers;

import java.nio.charset.StandardCharsets;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linkedsphere.notification_service.entity.NotificationEntity;
import com.linkedsphere.notification_service.repository.NotificationEntityRepository;
import com.linkedsphere.notification_service.service.PushNotificationService;
import com.linksphere.common.constants.RabbitMQConstants;
import com.linksphere.common.request.FCMNotificationRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventConsumer {

    private final ObjectMapper objectMapper;
    private final NotificationEntityRepository notificationEntityRepository;
    private final PushNotificationService pushNotificationService;

    @RabbitListener(queues = RabbitMQConstants.NOTIFICATION_QUEUE)
    public void consumeNotification(Object rawMessage) {
        try {
            FCMNotificationRequest request = null;

            if (rawMessage instanceof FCMNotificationRequest notifReq) {
                request = notifReq;
            } else if (rawMessage instanceof Message amqpMessage) {
                String json = new String(amqpMessage.getBody(), StandardCharsets.UTF_8);
                request = objectMapper.readValue(json, FCMNotificationRequest.class);
            } else if (rawMessage instanceof byte[] bytes) {
                String json = new String(bytes, StandardCharsets.UTF_8);
                request = objectMapper.readValue(json, FCMNotificationRequest.class);
            } else if (rawMessage instanceof String str) {
                request = objectMapper.readValue(str, FCMNotificationRequest.class);
            } else {
                String json = objectMapper.writeValueAsString(rawMessage);
                request = objectMapper.readValue(json, FCMNotificationRequest.class);
            }

            if (request != null && request.getRecipientId() != null) {
                NotificationEntity notificationEntity = NotificationEntity.builder()
                        .type(request.getType())
                        .recipientId(request.getRecipientId())
                        .actorId(request.getActorId())
                        .title(request.getTitle())
                        .message(request.getMessage())
                        .read(false)
                        .build();

                NotificationEntity saved = notificationEntityRepository.save(notificationEntity);
                log.info("Saved notification [{}] of type {} for recipientId: {}",
                        saved.getId(), request.getType(), request.getRecipientId());
            }
        } catch (Exception e) {
            log.error("Failed to process notification event: {}", e.getMessage(), e);
        }
    }
}
