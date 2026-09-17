package com.linkedsphere.post_service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;
import com.linkedsphere.post_service.enums.PostType;
import com.linkedsphere.post_service.enums.PostVisibility;
import com.linkedsphere.post_service.enums.PostStatus;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "posts")
public class Post {

    @Id
    private String id;

    private String authorId;

    private String content;

    private List<String> mediaUrls;

    private PostType postType;

    private PostVisibility visibility;

    private List<String> hashtags;

    private List<String> mentions;

    private long likeCount;

    private long commentCount;

    private long repostCount;

    private PostStatus status;

    private Instant createdAt;

    private Instant updatedAt;
}