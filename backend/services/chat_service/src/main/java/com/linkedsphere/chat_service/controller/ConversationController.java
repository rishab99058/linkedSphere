package com.linkedsphere.chat_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.linkedsphere.chat_service.dto.request.CreateConversationRequest;
import com.linkedsphere.chat_service.dto.response.ConversationResponse;
import com.linkedsphere.chat_service.service.ConversationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/conversations")
public class ConversationController {

    private final ConversationService conversationService;

    @PostMapping
    public ResponseEntity<ConversationResponse> createConversation(
            @RequestHeader("X-User-Id") String currentUserId,

            @Valid @RequestBody CreateConversationRequest request) {

        ConversationResponse response = conversationService.createConversation(
                currentUserId,
                request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{conversationId}")
    public ResponseEntity<ConversationResponse> getConversation(
            @RequestHeader("X-User-Id") String currentUserId,

            @PathVariable String conversationId) {

        ConversationResponse response = conversationService.getConversation(
                conversationId,
                currentUserId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ConversationResponse>> getUserConversations(
            @RequestHeader("X-User-Id") String currentUserId) {

        List<ConversationResponse> response = conversationService.getUserConversations(
                currentUserId);

        return ResponseEntity.ok(response);
    }

}
