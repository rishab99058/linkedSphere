package com.linkedsphere.post_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.linkedsphere.post_service.dto.request.CreateCommentRequest;
import com.linkedsphere.post_service.dto.response.PostCommentResponse;
import com.linkedsphere.post_service.security.user.AuthenticatedUser;
import com.linkedsphere.post_service.service.PostCommentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/posts/comments")
@RequiredArgsConstructor
public class PostCommentController {

    private final PostCommentService postCommentService;

    @PostMapping("/create")
    public ResponseEntity<PostCommentResponse> createComment(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody CreateCommentRequest request,
            @AuthenticationPrincipal AuthenticatedUser user) {
        PostCommentResponse response = postCommentService.createComment(request, user, authHeader);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PostCommentResponse>> getCommentsByPostId(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam("postId") String postId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        List<PostCommentResponse> response = postCommentService.getCommentsByPostId(postId, authHeader, page, limit);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteComment(
            @RequestParam("commentId") String commentId,
            @AuthenticationPrincipal AuthenticatedUser user) {
        postCommentService.deleteComment(commentId, user);
        return ResponseEntity.ok("Comment deleted successfully");
    }
}
