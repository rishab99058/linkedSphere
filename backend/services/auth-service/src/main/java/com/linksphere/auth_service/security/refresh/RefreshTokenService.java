package com.linksphere.auth_service.security.refresh;

import java.time.Duration;
import java.util.UUID;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final StringRedisTemplate redisTemplate;

    public void saveSession(
            String sessionId,
            UUID userId,
            long expirationSeconds) {

        String key = "refresh:" + sessionId;

        redisTemplate.opsForValue().set(
                key,
                userId.toString(),
                Duration.ofSeconds(expirationSeconds));
    }

    public UUID getUserId(String sessionId) {

        String key = "refresh:" + sessionId;

        String userId = redisTemplate.opsForValue().get(key);

        if (userId == null) {
            return null;
        }

        return UUID.fromString(userId);
    }

    public void deleteSession(String sessionId) {

        String key = "refresh:" + sessionId;

        redisTemplate.delete(key);
    }

}
