package com.linkedsphere.post_service.dto.request;

import java.util.List;

import lombok.Data;
import com.linkedsphere.post_service.enums.PostType;
import com.linkedsphere.post_service.enums.PostVisibility;

@Data
public class CreatePostRequest {

    private String userId;

    private String content;

    private PostType postType;

    private PostVisibility visibility;

    private List<String> hashtags;

    private List<String> mentions;
}
