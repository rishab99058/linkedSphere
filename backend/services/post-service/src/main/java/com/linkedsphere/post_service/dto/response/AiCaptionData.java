package com.linkedsphere.post_service.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiCaptionData {

    private String caption;

    private List<String> hashtags;

    private String category;

    private List<String> keywords;

    private String sentiment;
}
