package com.linkedsphere.post_service.service.impl;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.linkedsphere.post_service.config.UserEventPublisher;
import com.linkedsphere.post_service.dto.response.PostLikeResponse;
import com.linkedsphere.post_service.entity.Post;
import com.linkedsphere.post_service.entity.PostLike;
import com.linkedsphere.post_service.enums.PostStatus;
import com.linkedsphere.post_service.repository.PostLikeRepository;
import com.linkedsphere.post_service.repository.PostRepository;
import com.linkedsphere.post_service.service.PostLikeService;
import com.linksphere.common.enums.ErrorCode;
import com.linksphere.common.enums.NotificationType;
import com.linksphere.common.exception.BaseException;
import com.linksphere.common.request.FCMNotificationRequest;
import com.linksphere.common.request.NotificationData;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostLikeServiceImpl implements PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final UserEventPublisher userEventPublisher;

    @Override
    public PostLikeResponse likePost(String postId, String userId) {
        Post post = postRepository.findByIdAndStatusNot(postId, PostStatus.DELETED);
        if (post == null) {
            log.error("Post not found with ID: {}", postId);
            throw new BaseException(ErrorCode.RESOURCE_NOT_FOUND, "Post not found");
        }

        boolean alreadyLiked = postLikeRepository.existsByPostIdAndUserId(postId, userId);
        if (!alreadyLiked) {
            PostLike postLike = PostLike.builder()
                    .postId(postId)
                    .userId(userId)
                    .createdAt(Instant.now())
                    .build();
            postLikeRepository.save(postLike);

            post.setLikeCount(post.getLikeCount() + 1);
            postRepository.save(post);

            // Send notification to author if author is not the liker
            if (post.getAuthorId() != null && !post.getAuthorId().equals(userId)) {
                try {
                    UUID recipientId = UUID.fromString(post.getAuthorId());
                    UUID actorId = UUID.fromString(userId);

                    FCMNotificationRequest notif = FCMNotificationRequest.builder()
                            .recipientId(recipientId)
                            .actorId(actorId)
                            .type(NotificationType.POST_LIKED)
                            .title("Post Liked")
                            .message("Someone liked your post.")
                            .data(NotificationData.builder()
                                    .entityType("POST")
                                    .entityId(postId)
                                    .route("/post/" + postId)
                                    .build())
                            .build();

                    userEventPublisher.publishNotification(notif);
                } catch (Exception e) {
                    log.error("Failed to send like notification for post {}: {}", postId, e.getMessage());
                }
            }
        }

        long count = postLikeRepository.countByPostId(postId);
        return PostLikeResponse.builder()
                .postId(postId)
                .likeCount(count)
                .liked(true)
                .build();
    }

    @Override
    public PostLikeResponse unlikePost(String postId, String userId) {
        Post post = postRepository.findByIdAndStatusNot(postId, PostStatus.DELETED);
        if (post == null) {
            log.error("Post not found with ID: {}", postId);
            throw new BaseException(ErrorCode.RESOURCE_NOT_FOUND, "Post not found");
        }

        PostLike postLike = postLikeRepository.findByPostIdAndUserId(postId, userId).orElse(null);
        if (postLike != null) {
            postLikeRepository.delete(postLike);

            long currentCount = post.getLikeCount();
            post.setLikeCount(currentCount > 0 ? currentCount - 1 : 0);
            postRepository.save(post);
        }

        long count = postLikeRepository.countByPostId(postId);
        return PostLikeResponse.builder()
                .postId(postId)
                .likeCount(count)
                .liked(false)
                .build();
    }

    @Override
    public PostLikeResponse getPostLikes(String postId, String userId) {
        Post post = postRepository.findByIdAndStatusNot(postId, PostStatus.DELETED);
        if (post == null) {
            log.error("Post not found with ID: {}", postId);
            throw new BaseException(ErrorCode.RESOURCE_NOT_FOUND, "Post not found");
        }
        long count = postLikeRepository.countByPostId(postId);
        boolean isLiked = userId != null && postLikeRepository.existsByPostIdAndUserId(postId, userId);
        return PostLikeResponse.builder()
                .postId(postId)
                .likeCount(count)
                .liked(isLiked)
                .build();
    }

}
