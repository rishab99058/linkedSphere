package com.linkedsphere.post_service.dto.request;

import java.util.List;

import com.linkedsphere.post_service.enums.PostType;
import com.linkedsphere.post_service.enums.PostVisibility;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePostRequest {

    @NotBlank(message = "Post ID is required")
    private String postId;

    private String content;

    private PostType postType;

    private PostVisibility visibility;

    private List<String> hashtags;

    private List<String> mentions;

    private List<String> mediaUrls;
}
