package com.linksphere.common.events;

import java.time.Instant;
import java.util.UUID;

public record KafkaEvent<T>(
        UUID eventId,
        String eventType,
        Instant occurredAt,
        T payload) {
}
