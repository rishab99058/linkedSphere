package com.linkedsphere.post_service.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
}
