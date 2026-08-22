package com.linksphere.auth_service.messaging;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.linksphere.common.events.EventEnvelope;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KafkaEventPublisherImpl implements KafkaEventPublisher {

    private final KafkaTemplate<String, EventEnvelope> kafkaTemplate;

    @Override
    public void publish(
            String topic,
            String key,
            EventEnvelope event) {
        kafkaTemplate.send(topic, key, event);
    }

}
