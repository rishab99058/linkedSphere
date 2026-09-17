package com.linkedsphere.chat_service.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

import com.linkedsphere.chat_service.enums.ConversationType;

public record CreateConversationRequest(

        @NotNull(message = "Conversation type is required") ConversationType type,

        String name,

        String description,

        String avatarUrl,

        @NotNull(message = "Participant IDs are required") List<String> participantIds) {
}
