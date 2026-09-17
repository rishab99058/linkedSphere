package com.linkedsphere.post_service.dto.response;


import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import com.linkedsphere.post_service.enums.PostType;
import com.linkedsphere.post_service.enums.PostVisibility;
import com.linkedsphere.post_service.enums.PostStatus;

@Data
@Builder
public class PostResponse {

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
