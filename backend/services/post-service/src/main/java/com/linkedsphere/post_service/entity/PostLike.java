package com.linkedsphere.post_service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "post_likes")
@CompoundIndex(
        name = "post_user_unique",
        def = "{'postId': 1, 'userId': 1}",
        unique = true
)
public class PostLike {

    @Id
    private String id;

    private String postId;

    private String userId;

    private Instant createdAt;
}
