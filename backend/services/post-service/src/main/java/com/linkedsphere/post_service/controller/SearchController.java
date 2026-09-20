package com.linkedsphere.post_service.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.linkedsphere.post_service.dto.response.UnifiedSearchResponse;
import com.linkedsphere.post_service.entity.Post;
import com.linkedsphere.post_service.entity.PostSearchDocument;
import com.linkedsphere.post_service.entity.UserSearchDocument;
import com.linkedsphere.post_service.enums.PostStatus;
import com.linkedsphere.post_service.enums.SearchType;
import com.linkedsphere.post_service.repository.PostRepository;
import com.linkedsphere.post_service.service.PostSearchService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final PostSearchService postSearchService;
    private final PostRepository postRepository;

    @GetMapping
    public ResponseEntity<?> search(
            @RequestParam("query") String query,
            @RequestParam(value = "type", defaultValue = "ALL") SearchType type,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {

        if (query == null || query.isBlank()) {
            return ResponseEntity.badRequest().body("Query parameter cannot be empty");
        }

        switch (type) {
            case PEOPLE -> {
                Page<UserSearchDocument> people = postSearchService.searchPeople(query, page, limit);
                return ResponseEntity.ok(people);
            }
            case POSTS -> {
                Page<PostSearchDocument> posts = postSearchService.searchPosts(query, page, limit);
                return ResponseEntity.ok(posts);
            }
            case HASHTAGS -> {
                List<String> hashtags = postSearchService.searchHashtags(query, limit);
                return ResponseEntity.ok(hashtags);
            }
            case ALL -> {
                UnifiedSearchResponse response = postSearchService.searchAll(query, page, limit);
                return ResponseEntity.ok(response);
            }
            default -> {
                UnifiedSearchResponse response = postSearchService.searchAll(query, page, limit);
                return ResponseEntity.ok(response);
            }
        }
    }

    @PostMapping("/index-user")
    public ResponseEntity<String> indexUser(@RequestBody UserSearchDocument userDoc) {
        if (userDoc == null || userDoc.getId() == null) {
            return ResponseEntity.badRequest().body("User ID is required");
        }
        postSearchService.indexUser(userDoc);
        return ResponseEntity.ok("User indexed successfully in OpenSearch");
    }

    @PostMapping("/sync-posts")
    public ResponseEntity<String> syncAllPostsToOpenSearch() {
        List<Post> allPosts = postRepository.findAll();
        int indexedCount = 0;
        for (Post post : allPosts) {
            if (post.getStatus() != PostStatus.DELETED) {
                postSearchService.indexPost(post);
                indexedCount++;
            }
        }
        return ResponseEntity.ok("Synced " + indexedCount + " active posts to OpenSearch successfully");
    }
}
