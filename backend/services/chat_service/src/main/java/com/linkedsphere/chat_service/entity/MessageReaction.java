package com.linkedsphere.chat_service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "message_reactions")
public class MessageReaction {

    @Id
    private String id;

    private String messageId;

    private String conversationId;

    private String userId;

    private String emoji;

    private Instant createdAt;
}
