package com.linkedsphere.chat_service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.linkedsphere.chat_service.enums.ConversationMemberRole;
import com.linkedsphere.chat_service.enums.ConversationMemberStatus;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "conversation_members")
public class ConversationMember {

    @Id
    private String id;

    private String conversationId;

    private String userId;

    private ConversationMemberRole role;

    private String lastReadMessageId;

    private Instant lastReadAt;

    private boolean muted;

    private boolean archived;

    private ConversationMemberStatus status;

    private Instant joinedAt;

    private Instant updatedAt;
}
