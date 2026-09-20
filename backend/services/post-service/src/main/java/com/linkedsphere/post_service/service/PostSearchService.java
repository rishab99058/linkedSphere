package com.linkedsphere.post_service.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.opensearch.client.opensearch.core.search.Hit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.linkedsphere.post_service.config.OpenSearchIndexConfig;
import com.linkedsphere.post_service.dto.response.UnifiedSearchResponse;
import com.linkedsphere.post_service.entity.Post;
import com.linkedsphere.post_service.entity.PostSearchDocument;
import com.linkedsphere.post_service.entity.UserSearchDocument;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostSearchService {

    private final OpenSearchClient openSearchClient;

    // ==========================================
    // Indexing Methods
    // ==========================================

    public void indexPost(Post post) {
        try {
            PostSearchDocument document = PostSearchDocument.builder()
                    .id(post.getId())
                    .authorId(post.getAuthorId())
                    .content(post.getContent())
                    .hashtags(post.getHashtags() != null ? post.getHashtags() : Collections.emptyList())
                    .mentions(post.getMentions() != null ? post.getMentions() : Collections.emptyList())
                    .postType(post.getPostType() != null ? post.getPostType().name() : "TEXT")
                    .visibility(post.getVisibility() != null ? post.getVisibility().name() : "PUBLIC")
                    .status(post.getStatus() != null ? post.getStatus().name() : "ACTIVE")
                    .likeCount(post.getLikeCount())
                    .commentCount(post.getCommentCount())
                    .repostCount(post.getRepostCount())
                    .createdAt(post.getCreatedAt())
                    .updatedAt(post.getUpdatedAt())
                    .build();

            openSearchClient.index(i -> i
                    .index(OpenSearchIndexConfig.POSTS_INDEX)
                    .id(post.getId())
                    .document(document));

            log.info("Indexed post in OpenSearch with ID: {}", post.getId());
        } catch (Exception e) {
            log.error("Failed to index post {}: {}", post.getId(), e.getMessage());
        }
    }

    public void deletePostIndex(String postId) {
        try {
            openSearchClient.delete(d -> d
                    .index(OpenSearchIndexConfig.POSTS_INDEX)
                    .id(postId));
            log.info("Deleted post index for ID: {}", postId);
        } catch (Exception e) {
            log.error("Failed to delete post index {}: {}", postId, e.getMessage());
        }
    }

    public void indexUser(UserSearchDocument userDoc) {
        try {
            openSearchClient.index(i -> i
                    .index(OpenSearchIndexConfig.USERS_INDEX)
                    .id(userDoc.getId())
                    .document(userDoc));

            log.info("Indexed user in OpenSearch with ID: {}", userDoc.getId());
        } catch (Exception e) {
            log.error("Failed to index user {}: {}", userDoc.getId(), e.getMessage());
        }
    }

    // ==========================================
    // Search Methods
    // ==========================================

    public Page<PostSearchDocument> searchPosts(String query, int page, int size) {
        try {
            int from = page * size;
            SearchResponse<PostSearchDocument> response = openSearchClient.search(s -> s
                    .index(OpenSearchIndexConfig.POSTS_INDEX)
                    .from(from)
                    .size(size)
                    .query(q -> q.bool(b -> b
                            .must(m -> m.multiMatch(mm -> mm
                                    .query(query)
                                    .fields("content^3", "hashtags^2", "mentions")))
                            .mustNot(mn -> mn.term(t -> t.field("status").value(v -> v.stringValue("DELETED")))))),
                    PostSearchDocument.class);

            List<PostSearchDocument> posts = response.hits().hits().stream()
                    .map(Hit::source)
                    .filter(Objects::nonNull)
                    .toList();

            long total = response.hits().total() != null ? response.hits().total().value() : posts.size();

            return new PageImpl<>(posts, PageRequest.of(page, size), total);
        } catch (Exception e) {
            log.error("Error searching posts: {}", e.getMessage());
            return new PageImpl<>(Collections.emptyList(), PageRequest.of(page, size), 0);
        }
    }

    public Page<UserSearchDocument> searchPeople(String query, int page, int size) {
        try {
            int from = page * size;
            SearchResponse<UserSearchDocument> response = openSearchClient.search(s -> s
                    .index(OpenSearchIndexConfig.USERS_INDEX)
                    .from(from)
                    .size(size)
                    .query(q -> q.multiMatch(mm -> mm
                            .query(query)
                            .fields("fullName^3", "headline^2", "location", "industry"))),
                    UserSearchDocument.class);

            List<UserSearchDocument> users = response.hits().hits().stream()
                    .map(Hit::source)
                    .filter(Objects::nonNull)
                    .toList();

            long total = response.hits().total() != null ? response.hits().total().value() : users.size();

            return new PageImpl<>(users, PageRequest.of(page, size), total);
        } catch (Exception e) {
            log.error("Error searching people: {}", e.getMessage());
            return new PageImpl<>(Collections.emptyList(), PageRequest.of(page, size), 0);
        }
    }

    public List<String> searchHashtags(String query, int limit) {
        try {
            String cleanQuery = query.startsWith("#") ? query : "#" + query;
            SearchResponse<PostSearchDocument> response = openSearchClient.search(s -> s
                    .index(OpenSearchIndexConfig.POSTS_INDEX)
                    .size(Math.max(limit * 2, 20))
                    .query(q -> q.multiMatch(mm -> mm
                            .query(query)
                            .fields("hashtags"))),
                    PostSearchDocument.class);

            List<String> matchingHashtags = new ArrayList<>();
            response.hits().hits().forEach(hit -> {
                if (hit.source() != null && hit.source().getHashtags() != null) {
                    for (String ht : hit.source().getHashtags()) {
                        if (ht != null && ht.toLowerCase().contains(query.replace("#", "").toLowerCase())) {
                            if (!matchingHashtags.contains(ht)) {
                                matchingHashtags.add(ht);
                            }
                        }
                    }
                }
            });

            return matchingHashtags.stream().limit(limit).toList();
        } catch (Exception e) {
            log.error("Error searching hashtags: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public UnifiedSearchResponse searchAll(String query, int page, int size) {
        Page<UserSearchDocument> peoplePage = searchPeople(query, page, size);
        Page<PostSearchDocument> postsPage = searchPosts(query, page, size);
        List<String> hashtags = searchHashtags(query, 10);

        return UnifiedSearchResponse.builder()
                .query(query)
                .people(peoplePage.getContent())
                .posts(postsPage.getContent())
                .hashtags(hashtags)
                .totalPeople(peoplePage.getTotalElements())
                .totalPosts(postsPage.getTotalElements())
                .build();
    }
}