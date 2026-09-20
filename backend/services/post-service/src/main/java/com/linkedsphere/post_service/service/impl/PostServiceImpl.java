package com.linkedsphere.post_service.service.impl;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.linksphere.common.enums.ErrorCode;
import com.linksphere.common.exception.BaseException;
import com.linksphere.common.response.UserSummaryResponse;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

import com.linkedsphere.post_service.client.UserServiceClient;
import com.linkedsphere.post_service.config.UserEventPublisher;
import com.linkedsphere.post_service.dto.request.CreatePostRequest;
import com.linkedsphere.post_service.dto.request.UpdatePostRequest;
import com.linkedsphere.post_service.dto.response.FileUploadResponse;
import com.linkedsphere.post_service.dto.response.PostResponse;
import com.linkedsphere.post_service.dto.response.UserProfileClientResponse;
import com.linkedsphere.post_service.entity.Post;
import com.linkedsphere.post_service.entity.PostLike;
import com.linkedsphere.post_service.enums.PostStatus;
import com.linkedsphere.post_service.repository.PostLikeRepository;
import com.linkedsphere.post_service.repository.PostRepository;
import com.linkedsphere.post_service.security.user.AuthenticatedUser;
import com.linkedsphere.post_service.service.FeedTimelineService;
import com.linkedsphere.post_service.service.PostSearchService;
import com.linkedsphere.post_service.service.PostService;
import com.linkedsphere.post_service.uploads.ImageKitService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final ImageKitService imageKitService;
    private final UserServiceClient userServiceClient;
    private final PostSearchService postSearchService;
    private final FeedTimelineService feedTimelineService;

    @Override
    public PostResponse createPost(CreatePostRequest req, List<MultipartFile> media, AuthenticatedUser user) {
        String userId = req.getUserId();

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

        postSearchService.indexPost(savedPost);

        // Option B: Async Fan-out on write into Redis timeline caches
        try {
            feedTimelineService.fanOutPostToConnections(
                    savedPost.getId(),
                    savedPost.getAuthorId(),
                    savedPost.getCreatedAt().toEpochMilli()
            );
        } catch (Exception e) {
            log.error("Failed to trigger feed fanout for post {}: {}", savedPost.getId(), e.getMessage());
        }

        return mapToPostResponse(savedPost);
    }

    @Override
    public PostResponse updatePost(UpdatePostRequest req, List<MultipartFile> media, AuthenticatedUser user) {
        if (req.getPostId() == null || req.getPostId().isBlank()) {
            log.error("Post ID is required for updating post");
            throw new BaseException(ErrorCode.VALIDATION_FAILED, "Post ID is required");
        }

        Post post = postRepository.findByIdAndStatusNot(req.getPostId(), PostStatus.DELETED);
        if (post == null) {
            log.error("Post not found with ID: {}", req.getPostId());
            throw new BaseException(ErrorCode.RESOURCE_NOT_FOUND, "Post not found");
        }

        if (user != null && user.getUserId() != null) {
            String currentUserId = user.getUserId().toString();
            if (!currentUserId.equals(post.getAuthorId())) {
                log.error("User {} is not authorized to update post {}", currentUserId, post.getId());
                throw new BaseException(ErrorCode.ACCESS_DENIED, "You are not authorized to update this post");
            }
        }

        if (req.getContent() != null) {
            post.setContent(req.getContent());
        }
        if (req.getPostType() != null) {
            post.setPostType(req.getPostType());
        }
        if (req.getVisibility() != null) {
            post.setVisibility(req.getVisibility());
        }
        if (req.getHashtags() != null) {
            post.setHashtags(req.getHashtags());
        }
        if (req.getMentions() != null) {
            post.setMentions(req.getMentions());
        }

        List<String> currentMediaUrls = req.getMediaUrls() != null ? new ArrayList<>(req.getMediaUrls())
                : (post.getMediaUrls() != null ? new ArrayList<>(post.getMediaUrls()) : new ArrayList<>());

        if (media != null && !media.isEmpty()) {
            List<FileUploadResponse> uploadResponses = imageKitService.uploadFiles(media);
            List<String> newMediaUrls = uploadResponses.stream()
                    .map(FileUploadResponse::getUrl)
                    .filter(url -> url != null && !url.isBlank())
                    .toList();
            currentMediaUrls.addAll(newMediaUrls);
        }
        post.setMediaUrls(currentMediaUrls);
        post.setUpdatedAt(Instant.now());

        Post updatedPost = postRepository.save(post);
        log.info("Post updated successfully with ID: {}", updatedPost.getId());

        postSearchService.indexPost(updatedPost);

        return mapToPostResponse(updatedPost);
    }

    private PostResponse mapToPostResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
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

    @Override
    @CircuitBreaker(name = "user-service", fallbackMethod = "fetchAuthorSummaryFallback")
    @Retry(name = "user-service", fallbackMethod = "fetchAuthorSummaryFallback")
    public PostResponse getPostById(String postId, String authHeader) {
        Post post = postRepository.findByIdAndStatusNot(postId, PostStatus.DELETED);
        if (post == null) {
            log.error("Post not found with ID: {}", postId);
            throw new BaseException(ErrorCode.RESOURCE_NOT_FOUND, "Post not found");
        }

        // fetch user information

        PostResponse postResponse = mapToPostResponse(post);
        postResponse.setAuthor(fetchAuthorSummary(post.getAuthorId(), authHeader));
        return postResponse;
    }

    private UserProfileClientResponse fetchAuthorSummary(String authorId, String authHeader) {
        if (authorId == null || authorId.isBlank()) {
            return null;
        }
        try {
            // Feign Inter-service call with Authorization Header
            UserProfileClientResponse profile = userServiceClient.getById(authHeader, authorId);
            if (profile != null) {
                return UserProfileClientResponse.builder()
                        .id(profile.getId())
                        .fullName(profile.getFullName())
                        .headline(profile.getHeadline())
                        .profileImageUrl(profile.getProfileImageUrl())
                        .build();
            }
        } catch (Exception e) {
            // Fallback so post retrieval doesn't crash if user-service fails
            log.error("Error fetching author details for authorId: {}. Reason: {}", authorId, e.getMessage());
        }
        return UserProfileClientResponse.builder()
                .id(authorId)
                .build();
    }

    public UserProfileClientResponse fetchAuthorSummaryFallback(String authorId, String authHeader,
            Throwable throwable) {
        log.warn("User service is down! Returning fallback data for authorId: {}. Error: {}", authorId,
                throwable.getMessage());
        return UserProfileClientResponse.builder()
                .id(authorId)
                .fullName("LinkedIn Member")
                .build();
    }

    @Override
    @CircuitBreaker(name = "user-service", fallbackMethod = "fetchAuthorSummaryFallback")
    @Retry(name = "user-service", fallbackMethod = "fetchAuthorSummaryFallback")
    public List<PostResponse> getPostsByUserId(String userId, String authHeader, int page, int size) {
        if (userId == null || userId.isBlank()) {
            log.error("User ID is required for fetching posts");
            throw new BaseException(ErrorCode.VALIDATION_FAILED, "User ID is required");
        }
        Page<Post> pagePosts = postRepository.findByAuthorIdAndStatus(userId, PostStatus.ACTIVE,
                PageRequest.of(page, size));
        if (pagePosts.isEmpty()) {
            return Collections.emptyList();
        }

        List<PostResponse> postResponses = pagePosts.getContent().stream()
                .map(this::mapToPostResponse)
                .collect(Collectors.toList());
        UserProfileClientResponse author = fetchAuthorSummary(userId, authHeader);
        postResponses.forEach(post -> post.setAuthor(author));
        return postResponses;

    }

    @Override
    public String deletePost(String postId) {
        Post post = postRepository.findByIdAndStatusNot(postId, PostStatus.DELETED);
        if (post == null) {
            log.error("Post not found with ID: {}", postId);
            throw new BaseException(ErrorCode.RESOURCE_NOT_FOUND, "Post not found");
        }
        post.setStatus(PostStatus.DELETED);
        postRepository.save(post);
        postSearchService.deletePostIndex(postId);

        try {
            feedTimelineService.removePostFromTimelines(postId, post.getAuthorId());
        } catch (Exception e) {
            log.error("Failed to remove post {} from feed timelines: {}", postId, e.getMessage());
        }

        return "Post deleted successfully";
    }

    @Override
    public List<PostResponse> getMyArchivedPosts(String userId, int page, int size) {
        if (userId == null || userId.isBlank()) {
            log.error("User ID is required for fetching posts");
            throw new BaseException(ErrorCode.VALIDATION_FAILED, "User ID is required");
        }
        Page<Post> pagePosts = postRepository.findByAuthorIdAndStatus(userId, PostStatus.ARCHIVED,
                PageRequest.of(page, size));
        if (pagePosts.isEmpty()) {
            return Collections.emptyList();
        }

        List<PostResponse> postResponses = pagePosts.getContent().stream()
                .map(this::mapToPostResponse)
                .collect(Collectors.toList());

        return postResponses;

    }

    @Override
    public PostResponse archivePost(String postId) {
        Post post = postRepository.findByIdAndStatusNot(postId, PostStatus.DELETED);
        if (post == null) {
            log.error("Post not found with ID: {}", postId);
            throw new BaseException(ErrorCode.RESOURCE_NOT_FOUND, "Post not found");
        }
        post.setStatus(PostStatus.ARCHIVED);
        postRepository.save(post);

        try {
            feedTimelineService.removePostFromTimelines(postId, post.getAuthorId());
        } catch (Exception ignored) {
        }

        return mapToPostResponse(post);
    }

    @Override
    public PostResponse unarchivePost(String postId) {
        Post post = postRepository.findByIdAndStatusNot(postId, PostStatus.DELETED);
        if (post == null) {
            log.error("Post not found with ID: {}", postId);
            throw new BaseException(ErrorCode.RESOURCE_NOT_FOUND, "Post not found");
        }
        post.setStatus(PostStatus.ACTIVE);
        postRepository.save(post);

        try {
            feedTimelineService.fanOutPostToConnections(
                    post.getId(),
                    post.getAuthorId(),
                    post.getCreatedAt() != null ? post.getCreatedAt().toEpochMilli() : System.currentTimeMillis()
            );
        } catch (Exception ignored) {
        }

        return mapToPostResponse(post);
    }

    @Override
    public List<PostResponse> getFeed(AuthenticatedUser user, String authHeader, int page, int size) {
        String currentUserId = (user != null && user.getUserId() != null) ? user.getUserId().toString() : null;

        List<Post> posts = new ArrayList<>();

        // 1. Check Redis timeline cache first for logged-in user (< 2ms)
        if (currentUserId != null) {
            List<String> cachedPostIds = feedTimelineService.getTimelinePostIds(currentUserId, page, size);
            if (cachedPostIds != null && !cachedPostIds.isEmpty()) {
                Iterable<Post> postEntities = postRepository.findAllById(cachedPostIds);
                Map<String, Post> postMap = new java.util.HashMap<>();
                postEntities.forEach(p -> {
                    if (p.getStatus() == PostStatus.ACTIVE) {
                        postMap.put(p.getId(), p);
                    }
                });

                // Preserve timeline descending order
                for (String pId : cachedPostIds) {
                    Post p = postMap.get(pId);
                    if (p != null) {
                        posts.add(p);
                    }
                }
            }
        }

        // 2. Fallback / Cold Start / Cache Miss: Compute from MongoDB
        if (posts.isEmpty()) {
            List<String> targetAuthorIds = new ArrayList<>();
            if (currentUserId != null) {
                targetAuthorIds.add(currentUserId);
                try {
                    List<String> connectedUserIds = userServiceClient.getConnectedUserIds(authHeader, currentUserId);
                    if (connectedUserIds != null && !connectedUserIds.isEmpty()) {
                        targetAuthorIds.addAll(connectedUserIds);
                    }
                } catch (Exception e) {
                    log.warn("Failed to fetch connected user IDs for userId {}: {}", currentUserId, e.getMessage());
                }
            }

            Page<Post> postsPage;
            if (!targetAuthorIds.isEmpty()) {
                postsPage = postRepository.findByAuthorIdInAndStatusOrderByCreatedAtDesc(
                        targetAuthorIds, PostStatus.ACTIVE, PageRequest.of(page, size));

                if (postsPage.isEmpty()) {
                    postsPage = postRepository.findByStatusOrderByCreatedAtDesc(
                            PostStatus.ACTIVE, PageRequest.of(page, size));
                }
            } else {
                postsPage = postRepository.findByStatusOrderByCreatedAtDesc(
                        PostStatus.ACTIVE, PageRequest.of(page, size));
            }

            if (postsPage != null && !postsPage.isEmpty()) {
                posts = postsPage.getContent();
                // Asynchronously populate Redis timeline for this user
                if (currentUserId != null) {
                    final List<Post> postsToCache = posts;
                    feedTimelineService.populateUserTimeline(currentUserId, postsToCache);
                }
            }
        }

        if (posts.isEmpty()) {
            return java.util.Collections.emptyList();
        }

        // 3. Batch fetch author details
        List<String> authorIds = posts.stream()
                .map(Post::getAuthorId)
                .filter(id -> id != null && !id.isBlank())
                .distinct()
                .toList();

        Map<String, UserProfileClientResponse> authorMap = fetchAuthorsBatch(authorIds, authHeader);

        // 4. Batch fetch likes for current user
        Set<String> likedPostIds = new HashSet<>();
        if (currentUserId != null) {
            List<String> postIds = posts.stream().map(Post::getId).toList();
            List<PostLike> likes = postLikeRepository.findByUserIdAndPostIdIn(currentUserId, postIds);
            if (likes != null) {
                likedPostIds = likes.stream().map(PostLike::getPostId).collect(Collectors.toSet());
            }
        }

        final Set<String> finalLikedPostIds = likedPostIds;

        return posts.stream().map(post -> {
            PostResponse res = mapToPostResponse(post);
            res.setAuthor(authorMap.getOrDefault(post.getAuthorId(), UserProfileClientResponse.builder()
                    .id(post.getAuthorId())
                    .fullName("LinkedIn Member")
                    .build()));
            res.setLikedByCurrentUser(finalLikedPostIds.contains(post.getId()));
            return res;
        }).collect(Collectors.toList());
    }

    private Map<String, UserProfileClientResponse> fetchAuthorsBatch(List<String> authorIds, String authHeader) {
        if (authorIds == null || authorIds.isEmpty()) {
            return java.util.Collections.emptyMap();
        }
        try {
            List<UserSummaryResponse> userSummaries = userServiceClient.getBasicUsersByIds(authHeader, authorIds);
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
            log.error("Failed to fetch author profiles batch: {}", e.getMessage());
        }
        return java.util.Collections.emptyMap();
    }

}
