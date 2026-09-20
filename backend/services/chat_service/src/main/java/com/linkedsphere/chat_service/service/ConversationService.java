package com.linkedsphere.chat_service.service;

import java.util.List;

import com.linkedsphere.chat_service.dto.request.CreateConversationRequest;
import com.linkedsphere.chat_service.dto.response.ConversationResponse;

public interface ConversationService {

    ConversationResponse createConversation(
            String currentUserId,
            CreateConversationRequest request);

    ConversationResponse getConversation(
            String conversationId,
            String currentUserId);

    List<ConversationResponse> getUserConversations(
            String currentUserId);
}
