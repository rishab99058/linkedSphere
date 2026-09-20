package com.linkedsphere.post_service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "post_comments")
public class PostComment {

    @Id
    private String id;

    @Indexed
    private String postId;

    @Indexed
    private String userId;

    private String content;

    private Instant createdAt;

    private Instant updatedAt;
}
