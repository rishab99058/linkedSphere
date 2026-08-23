package com.linkedsphere.notification_service.consumers;

import org.springframework.stereotype.Component;

import com.linksphere.common.constants.RabbitMQConstants;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserEventConsumer {

    @RabbitListener(queues = RabbitMQConstants.USER_QUEUE)
    public void consumeUserCreated(String message) {

        log.info("Received message from RabbitMQ: {}", message);
    }

}
