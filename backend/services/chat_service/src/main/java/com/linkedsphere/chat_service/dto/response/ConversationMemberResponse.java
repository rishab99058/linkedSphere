package com.linkedsphere.chat_service.dto.response;

import java.time.Instant;

import com.linkedsphere.chat_service.enums.ConversationMemberRole;
import com.linkedsphere.chat_service.enums.ConversationMemberStatus;

public record ConversationMemberResponse(

        String userId,

        ConversationMemberRole role,

        ConversationMemberStatus status,

        String lastReadMessageId,

        Instant lastReadAt,

        boolean muted,

        boolean archived,

        Instant joinedAt) {
}
