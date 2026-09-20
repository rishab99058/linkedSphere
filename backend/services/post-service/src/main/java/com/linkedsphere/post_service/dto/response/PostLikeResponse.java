package com.linkedsphere.post_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostLikeResponse {

    private String postId;

    private long likeCount;

    private boolean liked;
}