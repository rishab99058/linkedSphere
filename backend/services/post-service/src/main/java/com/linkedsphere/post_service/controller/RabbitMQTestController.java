package com.linkedsphere.post_service.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkedsphere.post_service.config.UserEventPublisher;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rabbit")
@RequiredArgsConstructor
public class RabbitMQTestController {

    private final UserEventPublisher userEventPublisher;

    @PostMapping
    public Map<String, Object> sendMessage(@RequestBody(required = false) Map<String, Object> customPayload) {

        Map<String, Object> payload;

        if (customPayload != null && !customPayload.isEmpty()) {
            payload = customPayload;
        } else {
            payload = Map.of(
                    "postId", "123",
                    "authorId", "456",
                    "content", "I just built a Spring Boot microservice!",
                    "postType", "TEXT"
            );
        }

        // 1. Publish immediately to n8n.queue (instant response, no lag)
        userEventPublisher.publishN8nTrigger(payload);

        System.out.println("=========================================");
        System.out.println("🚀 [Post Published to n8n Queue]: " + payload);
        System.out.println("⏳ Waiting for n8n to process and reply on n8n.response.queue...");
        System.out.println("=========================================");

        return Map.of(
                "success", true,
                "message", "Message sent to n8n queue immediately. Response will be logged by Consumer as soon as n8n finishes.",
                "data", payload
        );
    }
}
