package com.linkedsphere.post_service.service.impl;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.linkedsphere.post_service.client.UserServiceClient;
import com.linkedsphere.post_service.config.UserEventPublisher;
import com.linkedsphere.post_service.dto.request.CreateCommentRequest;
import com.linkedsphere.post_service.dto.response.PostCommentResponse;
import com.linkedsphere.post_service.dto.response.UserProfileClientResponse;
import com.linkedsphere.post_service.entity.Post;
import com.linkedsphere.post_service.entity.PostComment;
import com.linkedsphere.post_service.enums.PostStatus;
import com.linkedsphere.post_service.repository.PostCommentRepository;
import com.linkedsphere.post_service.repository.PostRepository;
import com.linkedsphere.post_service.security.user.AuthenticatedUser;
import com.linkedsphere.post_service.service.PostCommentService;
import com.linksphere.common.enums.ErrorCode;
import com.linksphere.common.enums.NotificationType;
import com.linksphere.common.exception.BaseException;
import com.linksphere.common.request.FCMNotificationRequest;
import com.linksphere.common.request.NotificationData;
import com.linksphere.common.response.UserSummaryResponse;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostCommentServiceImpl implements PostCommentService {

    private final PostCommentRepository postCommentRepository;
    private final PostRepository postRepository;
    private final UserServiceClient userServiceClient;
    private final UserEventPublisher userEventPublisher;

    @Override
    public PostCommentResponse createComment(CreateCommentRequest request, AuthenticatedUser user, String authHeader) {
        Post post = postRepository.findByIdAndStatusNot(request.getPostId(), PostStatus.DELETED);
        if (post == null) {
            log.error("Post not found with ID: {}", request.getPostId());
            throw new BaseException(ErrorCode.RESOURCE_NOT_FOUND, "Post not found");
        }

        String userId = (user != null && user.getUserId() != null)
                ? user.getUserId().toString()
                : request.getUserId();

        if (userId == null || userId.isBlank()) {
            throw new BaseException(ErrorCode.VALIDATION_FAILED, "User ID is required");
        }

        PostComment comment = PostComment.builder()
                .postId(request.getPostId())
                .userId(userId)
                .content(request.getContent())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        PostComment savedComment = postCommentRepository.save(comment);

        // Update comment count on post
        post.setCommentCount(post.getCommentCount() + 1);
        postRepository.save(post);

        // Fetch user info for response
        UserProfileClientResponse authorSummary = fetchUserSummary(userId, authHeader);

        // Send notification to post author if commenter is not author
        if (post.getAuthorId() != null && !post.getAuthorId().equals(userId)) {
            try {
                UUID recipientId = UUID.fromString(post.getAuthorId());
                UUID actorId = UUID.fromString(userId);

                String commenterName = (authorSummary != null && authorSummary.getFullName() != null)
                        ? authorSummary.getFullName()
                        : "Someone";

                String commentSnippet = request.getContent() != null && request.getContent().length() > 60
                        ? request.getContent().substring(0, 60) + "..."
                        : request.getContent();

                FCMNotificationRequest notif = FCMNotificationRequest.builder()
                        .recipientId(recipientId)
                        .actorId(actorId)
                        .type(NotificationType.POST_COMMENTED)
                        .title("New Comment")
                        .message(commenterName + " commented: " + commentSnippet)
                        .data(NotificationData.builder()
                                .entityType("POST")
                                .entityId(post.getId())
                                .route("/post/" + post.getId())
                                .build())
                        .build();

                userEventPublisher.publishNotification(notif);
            } catch (Exception e) {
                log.error("Failed to send comment notification for post {}: {}", post.getId(), e.getMessage());
            }
        }

        return PostCommentResponse.builder()
                .id(savedComment.getId())
                .postId(savedComment.getPostId())
                .user(authorSummary)
                .content(savedComment.getContent())
                .createdAt(savedComment.getCreatedAt())
                .updatedAt(savedComment.getUpdatedAt())
                .build();
    }

    @Override
    public List<PostCommentResponse> getCommentsByPostId(String postId, String authHeader, int page, int size) {
        Post post = postRepository.findByIdAndStatusNot(postId, PostStatus.DELETED);
        if (post == null) {
            log.error("Post not found with ID: {}", postId);
            throw new BaseException(ErrorCode.RESOURCE_NOT_FOUND, "Post not found");
        }

        Page<PostComment> commentsPage = postCommentRepository.findByPostIdOrderByCreatedAtDesc(
                postId, PageRequest.of(page, size));

        if (commentsPage.isEmpty()) {
            return Collections.emptyList();
        }

        List<PostComment> comments = commentsPage.getContent();

        // Extract distinct user IDs
        List<String> userIds = comments.stream()
                .map(PostComment::getUserId)
                .filter(id -> id != null && !id.isBlank())
                .distinct()
                .toList();

        // Fetch user profiles in batch with authHeader
        Map<String, UserProfileClientResponse> userMap = fetchUsersBatch(userIds, authHeader);

        return comments.stream()
                .map(c -> PostCommentResponse.builder()
                        .id(c.getId())
                        .postId(c.getPostId())
                        .user(userMap.getOrDefault(c.getUserId(), UserProfileClientResponse.builder()
                                .id(c.getUserId())
                                .fullName("LinkedIn Member")
                                .build()))
                        .content(c.getContent())
                        .createdAt(c.getCreatedAt())
                        .updatedAt(c.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void deleteComment(String commentId, AuthenticatedUser user) {
        PostComment comment = postCommentRepository.findById(commentId).orElse(null);
        if (comment == null) {
            throw new BaseException(ErrorCode.RESOURCE_NOT_FOUND, "Comment not found");
        }

        if (user != null && user.getUserId() != null) {
            String currentUserId = user.getUserId().toString();
            if (!currentUserId.equals(comment.getUserId())) {
                throw new BaseException(ErrorCode.ACCESS_DENIED, "You are not authorized to delete this comment");
            }
        }

        postCommentRepository.delete(comment);

        // Decrement post comment count
        Post post = postRepository.findByIdAndStatusNot(comment.getPostId(), PostStatus.DELETED);
        if (post != null) {
            long currentCount = post.getCommentCount();
            post.setCommentCount(currentCount > 0 ? currentCount - 1 : 0);
            postRepository.save(post);
        }
    }

    private Map<String, UserProfileClientResponse> fetchUsersBatch(List<String> userIds, String authHeader) {
        if (userIds == null || userIds.isEmpty()) {
            return Collections.emptyMap();
        }
        try {
            List<UserSummaryResponse> userSummaries = userServiceClient.getBasicUsersByIds(authHeader, userIds);
            if (userSummaries != null) {
                return userSummaries.stream().collect(Collectors.toMap(
                        UserSummaryResponse::getUserId,
                        u -> UserProfileClientResponse.builder()
                                .id(u.getUserId())
                                .fullName(u.getFullName())
                                .profileImageUrl(u.getProfilePictureUrl())
                                .headline(u.getHeadline())
                                .build(),
                        (existing, replacement) -> existing
                ));
            }
        } catch (Exception e) {
            log.error("Failed to fetch user profiles batch: {}", e.getMessage());
        }
        return new HashMap<>();
    }

    private UserProfileClientResponse fetchUserSummary(String userId, String authHeader) {
        Map<String, UserProfileClientResponse> map = fetchUsersBatch(List.of(userId), authHeader);
        return map.getOrDefault(userId, UserProfileClientResponse.builder()
                .id(userId)
                .fullName("LinkedIn Member")
                .build());
    }
}
