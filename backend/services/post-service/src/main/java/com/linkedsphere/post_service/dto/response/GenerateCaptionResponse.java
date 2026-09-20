package com.linkedsphere.post_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenerateCaptionResponse {

    private String postId;

    private String authorId;

    private boolean success;

    private AiCaptionData ai;

    private String message;
}
