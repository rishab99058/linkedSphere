package com.linkedsphere.chat_service.dto.response;

import java.time.Instant;

import com.linkedsphere.chat_service.enums.ConversationType;

public record ConversationSummaryResponse(

        String id,

        ConversationType type,

        String name,

        String avatarUrl,

        String lastMessageId,

        String lastMessagePreview,

        Instant lastMessageAt,

        long unreadCount) {
}
