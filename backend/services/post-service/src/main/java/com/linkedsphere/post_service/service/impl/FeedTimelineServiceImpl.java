package com.linkedsphere.post_service.service.impl;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.linkedsphere.post_service.client.UserServiceClient;
import com.linkedsphere.post_service.entity.Post;
import com.linkedsphere.post_service.service.FeedTimelineService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedTimelineServiceImpl implements FeedTimelineService {

    private static final String FEED_KEY_PREFIX = "feed:";
    private static final int MAX_TIMELINE_SIZE = 500;
    private static final Duration TIMELINE_TTL = Duration.ofDays(7);

    private final StringRedisTemplate stringRedisTemplate;
    private final UserServiceClient userServiceClient;

    @Async
    @Override
    public void fanOutPostToConnections(String postId, String authorId, long timestampEpochMilli) {
        if (postId == null || authorId == null) {
            return;
        }

        try {
            log.info("Starting Async Fan-Out for post {} by author {}", postId, authorId);

            // 1. Target recipient user IDs: author + their connections
            List<String> targetUserIds = new ArrayList<>();
            targetUserIds.add(authorId);

            try {
                // Feign call without auth header or system fallback
                List<String> connections = userServiceClient.getConnectedUserIds(null, authorId);
                if (connections != null && !connections.isEmpty()) {
                    targetUserIds.addAll(connections);
                }
            } catch (Exception e) {
                log.warn("Failed to fetch connections for author {} during fan-out: {}", authorId, e.getMessage());
            }

            // 2. Fan-out write to Redis Sorted Set for each target user
            for (String userId : targetUserIds) {
                String feedKey = FEED_KEY_PREFIX + userId;
                try {
                    // Add postId with timestamp score (higher score = more recent)
                    stringRedisTemplate.opsForZSet().add(feedKey, postId, (double) timestampEpochMilli);

                    // Trim timeline to maintain only recent MAX_TIMELINE_SIZE posts
                    Long count = stringRedisTemplate.opsForZSet().zCard(feedKey);
                    if (count != null && count > MAX_TIMELINE_SIZE) {
                        stringRedisTemplate.opsForZSet().removeRange(feedKey, 0, count - MAX_TIMELINE_SIZE - 1);
                    }

                    // Refresh TTL
                    stringRedisTemplate.expire(feedKey, TIMELINE_TTL);
                } catch (Exception re) {
                    log.error("Failed to push post {} to feed of user {}: {}", postId, userId, re.getMessage());
                }
            }

            log.info("Completed Fan-Out of post {} to {} recipients", postId, targetUserIds.size());
        } catch (Exception e) {
            log.error("Fan-out failed for post {}: {}", postId, e.getMessage());
        }
    }

    @Async
    @Override
    public void removePostFromTimelines(String postId, String authorId) {
        if (postId == null) {
            return;
        }

        try {
            List<String> targetUserIds = new ArrayList<>();
            if (authorId != null) {
                targetUserIds.add(authorId);
                try {
                    List<String> connections = userServiceClient.getConnectedUserIds(null, authorId);
                    if (connections != null) {
                        targetUserIds.addAll(connections);
                    }
                } catch (Exception ignored) {
                }
            }

            for (String userId : targetUserIds) {
                String feedKey = FEED_KEY_PREFIX + userId;
                stringRedisTemplate.opsForZSet().remove(feedKey, postId);
            }

            log.info("Removed post {} from timelines of {} users", postId, targetUserIds.size());
        } catch (Exception e) {
            log.error("Failed to remove post {} from timelines: {}", postId, e.getMessage());
        }
    }

    @Override
    public List<String> getTimelinePostIds(String userId, int page, int size) {
        if (userId == null) {
            return Collections.emptyList();
        }

        String feedKey = FEED_KEY_PREFIX + userId;
        try {
            long start = (long) page * size;
            long end = start + size - 1;

            // Retrieve post IDs in descending score order (newest first)
            Set<String> postIdsSet = stringRedisTemplate.opsForZSet().reverseRange(feedKey, start, end);
            if (postIdsSet == null || postIdsSet.isEmpty()) {
                return Collections.emptyList();
            }

            return new ArrayList<>(postIdsSet);
        } catch (Exception e) {
            log.error("Error reading feed timeline from Redis for user {}: {}", userId, e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public void populateUserTimeline(String userId, List<Post> posts) {
        if (userId == null || posts == null || posts.isEmpty()) {
            return;
        }

        String feedKey = FEED_KEY_PREFIX + userId;
        try {
            for (Post post : posts) {
                if (post.getId() != null && post.getCreatedAt() != null) {
                    double score = (double) post.getCreatedAt().toEpochMilli();
                    stringRedisTemplate.opsForZSet().add(feedKey, post.getId(), score);
                }
            }
            stringRedisTemplate.expire(feedKey, TIMELINE_TTL);
            log.info("Populated Redis timeline for user {} with {} posts", userId, posts.size());
        } catch (Exception e) {
            log.error("Failed to populate Redis timeline for user {}: {}", userId, e.getMessage());
        }
    }
}
