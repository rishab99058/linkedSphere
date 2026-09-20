package com.linkedsphere.post_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentResponse {

    private String id;

    private String postId;

    private UserProfileClientResponse user;

    private String content;

    private Instant createdAt;

    private Instant updatedAt;
}