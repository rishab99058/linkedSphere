package com.linkedsphere.post_service.service.impl;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.linksphere.common.enums.ErrorCode;
import com.linksphere.common.exception.BaseException;
import com.linkedsphere.post_service.config.UserEventPublisher;
import com.linkedsphere.post_service.dto.request.CreatePostRequest;
import com.linkedsphere.post_service.dto.response.FileUploadResponse;
import com.linkedsphere.post_service.dto.response.PostResponse;
import com.linkedsphere.post_service.entity.Post;
import com.linkedsphere.post_service.enums.PostStatus;
import com.linkedsphere.post_service.repository.PostRepository;
import com.linkedsphere.post_service.security.user.AuthenticatedUser;
import com.linkedsphere.post_service.service.PostService;
import com.linkedsphere.post_service.uploads.ImageKitService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ImageKitService imageKitService;
    private final UserEventPublisher userEventPublisher;

    @Override
    public PostResponse createPost(CreatePostRequest req, List<MultipartFile> media, AuthenticatedUser user) {
        if (user == null || user.getUserId() == null) {
            log.error("Internal Server Error: Authenticated user is null");
            throw new BaseException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        UUID userId = user.getUserId();

        // 1. Parallel thread uploads for attached media files
        List<String> mediaUrls = Collections.emptyList();
        if (media != null && !media.isEmpty()) {
            List<FileUploadResponse> uploadResponses = imageKitService.uploadFiles(media);
            mediaUrls = uploadResponses.stream()
                    .map(FileUploadResponse::getUrl)
                    .filter(url -> url != null && !url.isBlank())
                    .toList();
        }

        // 2. Build and persist Post entity
        Post post = Post.builder()
                .authorId(userId.toString())
                .content(req.getContent())
                .mediaUrls(mediaUrls)
                .postType(req.getPostType())
                .visibility(req.getVisibility())
                .hashtags(req.getHashtags())
                .mentions(req.getMentions())
                .likeCount(0)
                .commentCount(0)
                .repostCount(0)
                .status(PostStatus.ACTIVE)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        Post savedPost = postRepository.save(post);
        log.info("Post created successfully with ID: {}", savedPost.getId());

        // 3. Publish POST_CREATED event to RabbitMQ
        publishPostCreatedEvent(savedPost, user);

        return mapToPostResponse(savedPost);
    }

    private void publishPostCreatedEvent(Post post, AuthenticatedUser user) {
        try {
            Map<String, Object> eventPayload = Map.ofEntries(
                    Map.entry("eventId", UUID.randomUUID().toString()),
                    Map.entry("eventType", "POST_CREATED"),
                    Map.entry("postId", post.getId() != null ? post.getId() : ""),
                    Map.entry("authorId", post.getAuthorId() != null ? post.getAuthorId() : ""),
                    Map.entry("authorEmail", user.getEmail() != null ? user.getEmail() : ""),
                    Map.entry("content", post.getContent() != null ? post.getContent() : ""),
                    Map.entry("mediaUrls", post.getMediaUrls() != null ? post.getMediaUrls() : List.of()),
                    Map.entry("postType", post.getPostType() != null ? post.getPostType().name() : ""),
                    Map.entry("visibility", post.getVisibility() != null ? post.getVisibility().name() : ""),
                    Map.entry("hashtags", post.getHashtags() != null ? post.getHashtags() : List.of()),
                    Map.entry("createdAt",
                            post.getCreatedAt() != null ? post.getCreatedAt().toString() : Instant.now().toString()));

            userEventPublisher.publishN8nTrigger(eventPayload);
        } catch (Exception e) {
            log.error("Failed to publish POST_CREATED event for post ID: {}", post.getId(), e);
        }
    }

    private PostResponse mapToPostResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .authorId(post.getAuthorId())
                .content(post.getContent())
                .mediaUrls(post.getMediaUrls())
                .postType(post.getPostType())
                .visibility(post.getVisibility())
                .hashtags(post.getHashtags())
                .mentions(post.getMentions())
                .likeCount(post.getLikeCount())
                .commentCount(post.getCommentCount())
                .repostCount(post.getRepostCount())
                .status(post.getStatus())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}
