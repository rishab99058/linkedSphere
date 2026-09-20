package com.linkedsphere.post_service.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.linkedsphere.post_service.dto.request.CreatePostRequest;
import com.linkedsphere.post_service.dto.request.GenerateCaptionRequest;
import com.linkedsphere.post_service.dto.request.UpdatePostRequest;
import com.linkedsphere.post_service.dto.response.GenerateCaptionResponse;
import com.linkedsphere.post_service.dto.response.PostResponse;
import com.linkedsphere.post_service.security.user.AuthenticatedUser;
import com.linkedsphere.post_service.service.AiCaptionService;
import com.linkedsphere.post_service.service.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final AiCaptionService aiCaptionService;

    @PostMapping("/generate-caption")
    public ResponseEntity<GenerateCaptionResponse> generateAiCaption(
            @Valid @RequestBody GenerateCaptionRequest request,
            @AuthenticationPrincipal AuthenticatedUser user) {
        GenerateCaptionResponse response = aiCaptionService.generateCaption(request, user);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostResponse> createPost(
            @Valid @RequestPart("post") CreatePostRequest request,
            @RequestPart(value = "media", required = false) List<MultipartFile> media,
            @AuthenticationPrincipal AuthenticatedUser user) {
        PostResponse response = postService.createPost(request, media, user);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostResponse> updatePost(
            @Valid @RequestPart("post") UpdatePostRequest request,
            @RequestPart(value = "media", required = false) List<MultipartFile> media,
            @AuthenticationPrincipal AuthenticatedUser user) {
        PostResponse response = postService.updatePost(request, media, user);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostResponse> updatePostJson(
            @Valid @RequestBody UpdatePostRequest request,
            @AuthenticationPrincipal AuthenticatedUser user) {
        PostResponse response = postService.updatePost(request, null, user);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get_by_id")
    public ResponseEntity<PostResponse> getPostById(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(value = "postId", required = true) String postId) {
        PostResponse response = postService.getPostById(postId, authHeader);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get_by_user_id")
    public ResponseEntity<List<PostResponse>> getPostsByUserId(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(value = "userId", required = true) String userId,
            @RequestParam(value = "page", required = true) int page,
            @RequestParam(value = "limit", required = true) int limit) {
        List<PostResponse> response = postService.getPostsByUserId(userId, authHeader, page, limit);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePost(
            @RequestParam(value = "postId", required = true) String postId) {
        String response = postService.deletePost(postId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get_my_archived_posts")
    public ResponseEntity<List<PostResponse>> getMyArchivedPosts(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(value = "userId", required = true) String userId,
            @RequestParam(value = "page", required = true) int page,
            @RequestParam(value = "limit", required = true) int limit) {
        List<PostResponse> response = postService.getMyArchivedPosts(userId, page, limit);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/archive")
    public ResponseEntity<PostResponse> archivePost(
            @RequestParam(value = "postId", required = true) String postId) {
        PostResponse response = postService.archivePost(postId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/unarchive")
    public ResponseEntity<PostResponse> unarchivePost(
            @RequestParam(value = "postId", required = true) String postId) {
        PostResponse response = postService.unarchivePost(postId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/feed")
    public ResponseEntity<List<PostResponse>> getFeed(
            @AuthenticationPrincipal AuthenticatedUser user,
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        List<PostResponse> response = postService.getFeed(user, authHeader, page, size);
        return ResponseEntity.ok(response);
    }
}
