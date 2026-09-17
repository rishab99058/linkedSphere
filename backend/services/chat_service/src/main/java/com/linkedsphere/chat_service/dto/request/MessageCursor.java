package com.linkedsphere.chat_service.dto.request;

import java.time.Instant;

public record MessageCursor(
        Instant createdAt,
        String id) {
}
