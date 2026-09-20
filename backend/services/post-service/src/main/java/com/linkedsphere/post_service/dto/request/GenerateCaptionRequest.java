package com.linkedsphere.post_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenerateCaptionRequest {

    private String postId;

    private String authorId;

    @NotBlank(message = "Prompt or caption idea cannot be empty")
    private String prompt;
}
