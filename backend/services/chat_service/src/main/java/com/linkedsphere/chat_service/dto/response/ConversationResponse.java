package com.linkedsphere.chat_service.dto.response;

import java.time.Instant;
import java.util.List;

import com.linkedsphere.chat_service.enums.ConversationType;

public record ConversationResponse(

        String id,

        ConversationType type,

        String name,

        String description,

        String avatarUrl,

        String createdBy,

        String lastMessageId,

        String lastMessagePreview,

        Instant lastMessageAt,

        Instant createdAt,

        Instant updatedAt,

        List<ConversationMemberResponse> members) {
}
