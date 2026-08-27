package com.linkedsphere.notification_service.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linkedsphere.notification_service.service.EmailService;
import com.linksphere.common.constants.RabbitMQConstants;
import com.linksphere.common.enums.EventType;
import com.linksphere.common.events.EventEnvelope;
import com.linksphere.common.request.PasswordResetOtpRequestedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class EmailEventCosumer {

    private final ObjectMapper objectMapper;
    private final EmailService emailService;

    @RabbitListener(queues = RabbitMQConstants.EMAIL_QUEUE)
    public void consumeEmailEvent(String message) {
        log.info("Received message from RabbitMQ: {}", message);
        try {
            EventEnvelope event = objectMapper.readValue(message, EventEnvelope.class);
            if (event.eventType() == EventType.PASSWORD_RESET_OTP_REQUESTED) {
                PasswordResetOtpRequestedEvent otpEvent = objectMapper.treeToValue(event.payload(),
                        PasswordResetOtpRequestedEvent.class);
                emailService.sendPasswordResetOtp(otpEvent.recipientEmail(), otpEvent.otp());
            }

        } catch (Exception e) {
            log.error("Error in consuming email event: {}", e.getMessage(), e);
        }

    }

}
