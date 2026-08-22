package com.linksphere.common.events;

import java.time.Instant;
import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;
import com.linksphere.common.enums.EventType;

public record EventEnvelope(
        UUID eventId,
        EventType eventType,
        Instant occurredAt,
        JsonNode payload) {

}
