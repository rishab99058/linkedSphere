package com.linkedsphere.chat_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.linkedsphere.chat_service.dto.request.GetMessagesRequest;
import com.linkedsphere.chat_service.dto.response.PaginatedMessageResponse;
import com.linkedsphere.chat_service.service.MessageService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping
    public ResponseEntity<PaginatedMessageResponse> getMessages(
            @RequestHeader("X-User-Id") String currentUserId,
            @RequestParam("conversationId") String conversationId,
            @Valid @ModelAttribute GetMessagesRequest request) {

        PaginatedMessageResponse response = messageService.getMessages(
                conversationId,
                currentUserId,
                request);

        return ResponseEntity.ok(response);
    }
}
