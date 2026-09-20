package com.linkedsphere.chat_service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.linkedsphere.chat_service.enums.MessageType;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "messages")
public class Message {

    @Id
    private String id;

    private String conversationId;

    private String senderId;

    private MessageType type;

    private MessageContent content;

    private List<MessageAttachment> attachments;

    private String replyToMessageId;

    private boolean deleted;

    private Instant createdAt;

    private Instant updatedAt;
}