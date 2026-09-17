package com.linkedsphere.chat_service.service;

import com.linkedsphere.chat_service.dto.request.CreateMessageRequest;
import com.linkedsphere.chat_service.dto.request.GetMessagesRequest;
import com.linkedsphere.chat_service.dto.response.MessageResponse;
import com.linkedsphere.chat_service.dto.response.PaginatedMessageResponse;

public interface MessageService {

    PaginatedMessageResponse getMessages(
            String conversationId,
            String currentUserId,
            GetMessagesRequest request);

    MessageResponse createMessage(
            String conversationId,
            String currentUserId,
            CreateMessageRequest request);

}