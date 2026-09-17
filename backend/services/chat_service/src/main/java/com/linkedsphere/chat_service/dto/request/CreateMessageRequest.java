package com.linkedsphere.chat_service.dto.request;

import java.util.List;

import com.linkedsphere.chat_service.entity.MessageAttachment;
import com.linkedsphere.chat_service.enums.MessageType;

import jakarta.validation.constraints.NotNull;

public record CreateMessageRequest(

        @NotNull MessageType type,

        String text,

        List<MessageAttachment> attachments,

        String replyToMessageId

) {
}
