package com.linkedsphere.post_service.consumers;

import java.nio.charset.StandardCharsets;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linkedsphere.post_service.entity.UserSearchDocument;
import com.linkedsphere.post_service.service.PostSearchService;
import com.linksphere.common.constants.RabbitMQConstants;
import com.linksphere.common.events.UserSyncEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserSyncConsumer {

    private final ObjectMapper objectMapper;
    private final PostSearchService postSearchService;

    @RabbitListener(queues = RabbitMQConstants.USER_SYNC_QUEUE)
    public void consumeUserSync(Object rawMessage) {
        try {
            UserSyncEvent event = null;

            if (rawMessage instanceof UserSyncEvent syncEvent) {
                event = syncEvent;
            } else if (rawMessage instanceof Message amqpMessage) {
                String json = new String(amqpMessage.getBody(), StandardCharsets.UTF_8);
                event = objectMapper.readValue(json, UserSyncEvent.class);
            } else if (rawMessage instanceof byte[] bytes) {
                String json = new String(bytes, StandardCharsets.UTF_8);
                event = objectMapper.readValue(json, UserSyncEvent.class);
            } else if (rawMessage instanceof String str) {
                event = objectMapper.readValue(str, UserSyncEvent.class);
            } else {
                String json = objectMapper.writeValueAsString(rawMessage);
                event = objectMapper.readValue(json, UserSyncEvent.class);
            }

            if (event != null && event.getUserId() != null) {
                UserSearchDocument userDoc = UserSearchDocument.builder()
                        .id(event.getUserId())
                        .fullName(event.getFullName())
                        .headline(event.getHeadline())
                        .location(event.getLocation())
                        .industry(event.getIndustry())
                        .profilePictureUrl(event.getProfilePictureUrl())
                        .build();

                postSearchService.indexUser(userDoc);
                log.info("Successfully synced user profile to OpenSearch for userId: {}", event.getUserId());
            }
        } catch (Exception e) {
            log.error("Failed to process UserSyncEvent: {}", e.getMessage(), e);
        }
    }
}
