package com.linkedsphere.post_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.linkedsphere.post_service.dto.response.PostLikeResponse;
import com.linkedsphere.post_service.security.user.AuthenticatedUser;
import com.linkedsphere.post_service.service.PostLikeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/posts/likes")
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/like")
    public ResponseEntity<PostLikeResponse> likePost(
            @RequestParam("postId") String postId,
            @RequestParam(value = "userId", required = false) String userIdParam,
            @AuthenticationPrincipal AuthenticatedUser user) {
        String userId = (user != null && user.getUserId() != null) ? user.getUserId().toString() : userIdParam;
        PostLikeResponse response = postLikeService.likePost(postId, userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/unlike")
    public ResponseEntity<PostLikeResponse> unlikePost(
            @RequestParam("postId") String postId,
            @RequestParam(value = "userId", required = false) String userIdParam,
            @AuthenticationPrincipal AuthenticatedUser user) {
        String userId = (user != null && user.getUserId() != null) ? user.getUserId().toString() : userIdParam;
        PostLikeResponse response = postLikeService.unlikePost(postId, userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<PostLikeResponse> getPostLikes(
            @RequestParam("postId") String postId,
            @RequestParam(value = "userId", required = false) String userIdParam,
            @AuthenticationPrincipal AuthenticatedUser user) {
        String userId = (user != null && user.getUserId() != null) ? user.getUserId().toString() : userIdParam;
        PostLikeResponse response = postLikeService.getPostLikes(postId, userId);
        return ResponseEntity.ok(response);
    }
}
