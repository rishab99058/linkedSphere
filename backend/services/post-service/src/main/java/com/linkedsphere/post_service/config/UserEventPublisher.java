package com.linkedsphere.post_service.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.linksphere.common.constants.RabbitMQConstants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishN8nTrigger(Object message) {
        log.info("Publishing event to exchange '{}' with routing key '{}': {}",
                RabbitMQConstants.EXCHANGE,
                RabbitMQConstants.N8N_TRIGGER_RECEIVED,
                message);

        rabbitTemplate.convertAndSend(
                RabbitMQConstants.EXCHANGE,
                RabbitMQConstants.N8N_TRIGGER_RECEIVED,
                message
        );
    }

    public Object sendAndReceiveN8nTrigger(Object message) {
        log.info("Sending request to n8n and waiting for response...");

        Object response = rabbitTemplate.convertSendAndReceive(
                RabbitMQConstants.EXCHANGE,
                RabbitMQConstants.N8N_TRIGGER_RECEIVED,
                message
        );

        String responseStr = response != null ? response.toString() : "null (No response or timeout)";
        log.info("Received response from n8n: {}", responseStr);
        System.out.println("=========================================");
        System.out.println("📩 [n8n Response]: " + responseStr);
        System.out.println("=========================================");

        return response;
    }

    @Async
    public void publishNotification(com.linksphere.common.request.FCMNotificationRequest request) {
        try {
            log.info("Publishing notification to exchange '{}' with routing key '{}' for recipientId '{}'",
                    RabbitMQConstants.EXCHANGE,
                    RabbitMQConstants.NOTIFICATION_CREATED,
                    request.getRecipientId());

            rabbitTemplate.convertAndSend(
                    RabbitMQConstants.EXCHANGE,
                    RabbitMQConstants.NOTIFICATION_CREATED,
                    request
            );
        } catch (Exception e) {
            log.error("Failed to publish notification for recipientId {}: {}", request.getRecipientId(), e.getMessage());
        }
    }
}
