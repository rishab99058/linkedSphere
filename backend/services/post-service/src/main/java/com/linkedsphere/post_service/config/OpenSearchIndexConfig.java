package com.linkedsphere.post_service.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.indices.CreateIndexRequest;
import org.opensearch.client.opensearch.indices.ExistsRequest;
import org.opensearch.client.opensearch._types.mapping.Property;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import java.io.IOException;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class OpenSearchIndexConfig {

    private final OpenSearchClient openSearchClient;

    public static final String POSTS_INDEX = "posts";
    public static final String USERS_INDEX = "users";

    @PostConstruct
    public void initializeIndices() {
        try {
            createPostsIndex();
            createUsersIndex();
        } catch (Exception e) {
            log.error("Failed to initialize OpenSearch indices: {}", e.getMessage());
        }
    }

    private void createPostsIndex() throws IOException {
        boolean exists = openSearchClient.indices()
                .exists(ExistsRequest.of(e -> e.index(POSTS_INDEX)))
                .value();

        if (exists) {
            return;
        }

        openSearchClient.indices().create(
                CreateIndexRequest.of(c -> c
                        .index(POSTS_INDEX)
                        .mappings(m -> m
                                .properties("id",
                                        Property.of(p -> p.keyword(k -> k)))
                                .properties("authorId",
                                        Property.of(p -> p.keyword(k -> k)))
                                .properties("content",
                                        Property.of(p -> p.text(t -> t)))
                                .properties("hashtags",
                                        Property.of(p -> p.keyword(k -> k)))
                                .properties("mentions",
                                        Property.of(p -> p.keyword(k -> k)))
                                .properties("postType",
                                        Property.of(p -> p.keyword(k -> k)))
                                .properties("visibility",
                                        Property.of(p -> p.keyword(k -> k)))
                                .properties("status",
                                        Property.of(p -> p.keyword(k -> k)))
                                .properties("likeCount",
                                        Property.of(p -> p.long_(l -> l)))
                                .properties("commentCount",
                                        Property.of(p -> p.long_(l -> l)))
                                .properties("repostCount",
                                        Property.of(p -> p.long_(l -> l)))
                                .properties("createdAt",
                                        Property.of(p -> p.date(d -> d)))
                                .properties("updatedAt",
                                        Property.of(p -> p.date(d -> d))))));
        log.info("✅ OpenSearch index '{}' created successfully", POSTS_INDEX);
    }

    private void createUsersIndex() throws IOException {
        boolean exists = openSearchClient.indices()
                .exists(ExistsRequest.of(e -> e.index(USERS_INDEX)))
                .value();

        if (exists) {
            return;
        }

        openSearchClient.indices().create(
                CreateIndexRequest.of(c -> c
                        .index(USERS_INDEX)
                        .mappings(m -> m
                                .properties("id",
                                        Property.of(p -> p.keyword(k -> k)))
                                .properties("fullName",
                                        Property.of(p -> p.text(t -> t)))
                                .properties("headline",
                                        Property.of(p -> p.text(t -> t)))
                                .properties("location",
                                        Property.of(p -> p.text(t -> t)))
                                .properties("industry",
                                        Property.of(p -> p.text(t -> t)))
                                .properties("profilePictureUrl",
                                        Property.of(p -> p.keyword(k -> k))))));
        log.info("✅ OpenSearch index '{}' created successfully", USERS_INDEX);
    }
}