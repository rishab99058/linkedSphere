package com.linkedsphere.post_service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostSearchDocument {

    @Id
    private String id;

    private String authorId;

    private String content;

    private List<String> hashtags;

    private List<String> mentions;

    private String postType;

    private String visibility;

    private String status;

    private long likeCount;

    private long commentCount;

    private long repostCount;

    private Instant createdAt;

    private Instant updatedAt;
}