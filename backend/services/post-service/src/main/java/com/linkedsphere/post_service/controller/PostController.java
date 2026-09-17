package com.linkedsphere.post_service.controller;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.linkedsphere.post_service.config.UserEventPublisher;
import com.linkedsphere.post_service.dto.request.CreatePostRequest;
import com.linkedsphere.post_service.dto.response.PostResponse;
import com.linksphere.common.constants.RabbitMQConstants;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.linkedsphere.post_service.security.user.AuthenticatedUser;
import com.linkedsphere.post_service.service.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserEventPublisher userEventPublisher;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostResponse> createPost(
            @RequestPart("post") CreatePostRequest request,
            @RequestPart(value = "media", required = false) List<MultipartFile> media,
            @AuthenticationPrincipal AuthenticatedUser user
    ) {
        PostResponse response = postService.createPost(request, media, user);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/test")
    public ResponseEntity<Map<String, Object>> testN8nTriggerGet() {
        Map<String, Object> dummyData = createDummyPostPayload();

        userEventPublisher.publishN8nTrigger(dummyData);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Dummy event published successfully to n8n queue",
                "exchange", RabbitMQConstants.EXCHANGE,
                "routingKey", RabbitMQConstants.N8N_TRIGGER_RECEIVED,
                "queue", RabbitMQConstants.N8N_QUEUE,
                "data", dummyData
        ));
    }

    @PostMapping("/test")
    public ResponseEntity<Map<String, Object>> testN8nTriggerPost(@RequestBody(required = false) Map<String, Object> customPayload) {
        Map<String, Object> payload = (customPayload != null && !customPayload.isEmpty())
                ? customPayload
                : createDummyPostPayload();

        userEventPublisher.publishN8nTrigger(payload);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Event published successfully to n8n queue",
                "exchange", RabbitMQConstants.EXCHANGE,
                "routingKey", RabbitMQConstants.N8N_TRIGGER_RECEIVED,
                "queue", RabbitMQConstants.N8N_QUEUE,
                "data", payload
        ));
    }

    private Map<String, Object> createDummyPostPayload() {
        return Map.of(
                "eventId", UUID.randomUUID().toString(),
                "eventType", "POST_CREATED",
                "postId", UUID.randomUUID().toString(),
                "authorId", "user-" + UUID.randomUUID().toString().substring(0, 8),
                "authorEmail", "testuser@linksphere.com",
                "content", "Excited to share that we just integrated n8n workflows with LinkSphere! 🚀 #tech #automation #spring",
                "postType", "TEXT",
                "visibility", "PUBLIC",
                "hashtags", List.of("tech", "automation", "spring"),
                "createdAt", Instant.now().toString()
        );
    }
}
