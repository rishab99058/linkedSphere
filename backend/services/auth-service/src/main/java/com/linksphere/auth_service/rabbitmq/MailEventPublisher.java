package com.linksphere.auth_service.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linksphere.common.constants.RabbitMQConstants;
import com.linksphere.common.events.EventEnvelope;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MailEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    // 1. Password OTP Mail
    public void sendPasswordResetOTPMail(EventEnvelope event) {

        try {
            String message = objectMapper.writeValueAsString(event);
            rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGE, RabbitMQConstants.EMAIL_SEND, message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send password reset OTP mail", e);
        }

    }

}
