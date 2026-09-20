package com.linkedsphere.chat_service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.linkedsphere.chat_service.enums.ConversationType;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "conversations")
public class Conversation {

    @Id
    private String id;

    private ConversationType type;

    private String name;

    private String description;

    private String avatarUrl;

    private String createdBy;

    private String lastMessageId;

    private String lastMessagePreview;

    private Instant lastMessageAt;

    private Instant createdAt;

    private Instant updatedAt;
}
