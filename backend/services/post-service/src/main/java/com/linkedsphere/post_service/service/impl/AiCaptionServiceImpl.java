package com.linkedsphere.post_service.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linkedsphere.post_service.config.UserEventPublisher;
import com.linkedsphere.post_service.dto.request.GenerateCaptionRequest;
import com.linkedsphere.post_service.dto.response.AiCaptionData;
import com.linkedsphere.post_service.dto.response.GenerateCaptionResponse;
import com.linkedsphere.post_service.security.user.AuthenticatedUser;
import com.linkedsphere.post_service.service.AiCaptionService;
import com.linksphere.common.enums.ErrorCode;
import com.linksphere.common.exception.BaseException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiCaptionServiceImpl implements AiCaptionService {

    private final UserEventPublisher userEventPublisher;
    private final ObjectMapper objectMapper;

    // Thread-safe map to correlate async n8n responses with pending HTTP requests
    // by postId/requestId
    private final Map<String, CompletableFuture<GenerateCaptionResponse>> pendingRequests = new ConcurrentHashMap<>();

    @Override
    public GenerateCaptionResponse generateCaption(GenerateCaptionRequest request, AuthenticatedUser user) {
        String correlationId = (request.getPostId() != null && !request.getPostId().isBlank())
                ? request.getPostId()
                : UUID.randomUUID().toString();

        String authorId = (user != null && user.getUserId() != null)
                ? user.getUserId().toString()
                : (request.getAuthorId() != null && !request.getAuthorId().isBlank() ? request.getAuthorId()
                        : "anonymous");

        CompletableFuture<GenerateCaptionResponse> future = new CompletableFuture<>();
        pendingRequests.put(correlationId, future);

        Map<String, Object> payload = new HashMap<>();
        payload.put("postId", correlationId);
        payload.put("requestId", correlationId);
        payload.put("authorId", authorId);
        payload.put("content", request.getPrompt());
        payload.put("postType", "TEXT");

        log.info("🚀 Publishing AI Caption Generation request with correlationId (postId): {}, authorId: {}",
                correlationId, authorId);
        userEventPublisher.publishN8nTrigger(payload);

        try {
            // Wait up to 35 seconds for n8n to process and reply
            return future.get(35, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            log.error("⏳ Timeout waiting for n8n AI response for id: {}", correlationId);
            throw new BaseException(ErrorCode.INTERNAL_SERVER_ERROR, "AI Caption service timed out. Please try again.");
        } catch (Exception e) {
            log.error("❌ Error while waiting for n8n AI response: {}", e.getMessage());
            throw new BaseException(ErrorCode.INTERNAL_SERVER_ERROR,
                    "Failed to generate AI caption: " + e.getMessage());
        } finally {
            pendingRequests.remove(correlationId);
        }
    }

    @Override
    public void handleN8nResponse(String responseJson) {
        try {
            JsonNode rootNode = objectMapper.readTree(responseJson);

            // If n8n returns an array, pick the first element
            if (rootNode.isArray() && !rootNode.isEmpty()) {
                rootNode = rootNode.get(0);
            }

            // Check if there's an inner 'text' or 'response' or 'output' node that contains
            // JSON string
            JsonNode contentNode = rootNode;
            if (rootNode.hasNonNull("text")) {
                String textStr = rootNode.get("text").asText().trim();
                if (textStr.startsWith("```json")) {
                    textStr = textStr.substring(7);
                }
                if (textStr.startsWith("```")) {
                    textStr = textStr.substring(3);
                }
                if (textStr.endsWith("```")) {
                    textStr = textStr.substring(0, textStr.length() - 3);
                }
                textStr = textStr.trim();
                try {
                    contentNode = objectMapper.readTree(textStr);
                } catch (Exception ignored) {
                }
            } else if (rootNode.hasNonNull("response")) {
                contentNode = rootNode.get("response");
            } else if (rootNode.hasNonNull("output")) {
                contentNode = rootNode.get("output");
            }

            String postId = rootNode.hasNonNull("postId") ? rootNode.get("postId").asText() : null;
            if (postId == null && rootNode.hasNonNull("requestId")) {
                postId = rootNode.get("requestId").asText();
            }
            if (postId == null && contentNode.hasNonNull("postId")) {
                postId = contentNode.get("postId").asText();
            }

            // If postId is null from n8n but we have an active waiting request, match it!
            if (postId == null && !pendingRequests.isEmpty()) {
                postId = pendingRequests.keySet().iterator().next();
                log.info("ℹ️ PostId was null in n8n response, auto-correlated with single active pending request: {}",
                        postId);
            }

            String authorId = rootNode.hasNonNull("authorId") ? rootNode.get("authorId").asText() : null;
            if (authorId == null && contentNode.hasNonNull("authorId")) {
                authorId = contentNode.get("authorId").asText();
            }
            boolean success = rootNode.has("success") ? rootNode.get("success").asBoolean(true) : true;

            AiCaptionData aiData = null;
            JsonNode targetAiNode = contentNode.has("ai") ? contentNode.get("ai") : contentNode;

            if (targetAiNode.hasNonNull("caption") || targetAiNode.hasNonNull("hashtags")) {
                String caption = targetAiNode.hasNonNull("caption") ? targetAiNode.get("caption").asText() : "";
                String category = targetAiNode.hasNonNull("category") ? targetAiNode.get("category").asText() : "";
                String sentiment = targetAiNode.hasNonNull("sentiment") ? targetAiNode.get("sentiment").asText() : "";

                List<String> hashtags = new ArrayList<>();
                if (targetAiNode.has("hashtags") && targetAiNode.get("hashtags").isArray()) {
                    targetAiNode.get("hashtags").forEach(h -> hashtags.add(h.asText()));
                }

                List<String> keywords = new ArrayList<>();
                if (targetAiNode.has("keywords") && targetAiNode.get("keywords").isArray()) {
                    targetAiNode.get("keywords").forEach(k -> keywords.add(k.asText()));
                }

                aiData = AiCaptionData.builder()
                        .caption(caption)
                        .hashtags(hashtags)
                        .category(category)
                        .keywords(keywords)
                        .sentiment(sentiment)
                        .build();
            }

            GenerateCaptionResponse response = GenerateCaptionResponse.builder()
                    .postId(postId)
                    .authorId(authorId)
                    .success(success)
                    .ai(aiData)
                    .message("AI caption and hashtags generated successfully")
                    .build();

            if (postId != null && pendingRequests.containsKey(postId)) {
                CompletableFuture<GenerateCaptionResponse> future = pendingRequests.remove(postId);
                if (future != null) {
                    future.complete(response);
                    log.info("✅ Successfully matched and completed AI request for postId: {}", postId);
                }
            } else {
                log.info(
                        "ℹ️ Received n8n response with postId: {}, but no active HTTP thread waiting (may have timed out or async).",
                        postId);
            }

        } catch (Exception e) {
            log.error("❌ Failed to parse n8n response JSON: {}", e.getMessage(), e);
        }
    }
}
