package com.linksphere.auth_service.messaging;

import com.linksphere.common.events.EventEnvelope;

public interface KafkaEventPublisher {

    void publish(
            String topic,
            String key,
            EventEnvelope event);
}
