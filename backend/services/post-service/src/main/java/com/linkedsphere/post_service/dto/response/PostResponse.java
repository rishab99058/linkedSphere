package com.linkedsphere.post_service.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.linkedsphere.post_service.enums.PostStatus;
import com.linkedsphere.post_service.enums.PostType;
import com.linkedsphere.post_service.enums.PostVisibility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {

    private String id;

    private UserProfileClientResponse author;

    private String content;

    private List<String> mediaUrls;

    private PostType postType;

    private PostVisibility visibility;

    private List<String> hashtags;

    private List<String> mentions;

    private long likeCount;

    private long commentCount;

    private long repostCount;

    private Boolean likedByCurrentUser;

    private PostStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Instant createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Instant updatedAt;
}