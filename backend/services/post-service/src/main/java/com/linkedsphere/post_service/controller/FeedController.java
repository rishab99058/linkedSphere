package com.linkedsphere.post_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.linkedsphere.post_service.dto.response.PostResponse;
import com.linkedsphere.post_service.security.user.AuthenticatedUser;
import com.linkedsphere.post_service.service.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping({"/feed", "/api/v1/feed"})
@RequiredArgsConstructor
public class FeedController {

    private final PostService postService;

    /**
     * Get user personalized feed (cached in Redis with fan-out + MongoDB fallback)
     * 
     * @param user       authenticated user from JWT
     * @param authHeader Bearer token forwarded for Feign calls
     * @param page       page number (0-indexed)
     * @param size       number of posts per page (default: 10)
     * @return List of enriched PostResponse
     */
    @GetMapping
    public ResponseEntity<List<PostResponse>> getFeed(
            @AuthenticationPrincipal AuthenticatedUser user,
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("Fetching feed for userId: {}, page: {}, size: {}", 
                user != null ? user.getUserId() : "Anonymous", page, size);
        List<PostResponse> response = postService.getFeed(user, authHeader, page, size);
        return ResponseEntity.ok(response);
    }
}
