package com.linkedsphere.notification_service.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linkedsphere.notification_service.service.EmailService;
import com.linksphere.common.enums.EventType;
import com.linksphere.common.events.EventEnvelope;
import com.linksphere.common.request.PasswordResetOtpRequestedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaNotificationConsumer {

    private final ObjectMapper objectMapper;
    private final EmailService emailService;

    @KafkaListener(topics = "notification-events", groupId = "notification-service", containerFactory = "kafkaListenerContainerFactory")
    public void consume(EventEnvelope envelope) {
        log.info("Received notification event: eventId={}, eventType={}",
                envelope.eventId(), envelope.eventType());

        if (envelope.eventType() == EventType.PASSWORD_RESET_OTP_REQUESTED) {
            try {
                PasswordResetOtpRequestedEvent event = objectMapper.treeToValue(
                        envelope.payload(), PasswordResetOtpRequestedEvent.class);

                emailService.sendPasswordResetOtp(event.recipientEmail(), event.otp());

            } catch (JsonProcessingException | IllegalArgumentException e) {
                log.error("Failed to process PasswordResetOtpRequestedEvent: {}", e.getMessage());
            }
        }
    }
}
