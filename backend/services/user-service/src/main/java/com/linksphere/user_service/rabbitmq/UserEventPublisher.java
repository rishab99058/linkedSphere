package com.linksphere.user_service.rabbitmq;

import com.linksphere.common.constants.RabbitMQConstants;
import com.linksphere.common.events.UserSyncEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Async
    public void publishUserSync(UserSyncEvent event) {
        try {
            rabbitTemplate.convertAndSend(
                    RabbitMQConstants.EXCHANGE,
                    RabbitMQConstants.USER_SYNC,
                    event
            );
            log.info("Published UserSyncEvent for userId: {}", event.getUserId());
        } catch (Exception e) {
            log.error("Failed to publish UserSyncEvent for userId {}: {}", event.getUserId(), e.getMessage());
        }
    }
}
