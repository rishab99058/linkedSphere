package com.linkedsphere.chat_service.dto.response;

import java.time.Instant;
import java.util.List;

import com.linkedsphere.chat_service.entity.MessageAttachment;
import com.linkedsphere.chat_service.entity.MessageContent;
import com.linkedsphere.chat_service.enums.MessageType;

public record MessageResponse(
        String id,
        String conversationId,
        String senderId,
        MessageType type,
        MessageContent content,
        List<MessageAttachment> attachments,
        String replyToMessageId,
        boolean deleted,
        Instant createdAt,
        Instant updatedAt) {
}
