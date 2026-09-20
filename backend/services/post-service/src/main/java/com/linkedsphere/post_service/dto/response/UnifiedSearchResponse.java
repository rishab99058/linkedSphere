package com.linkedsphere.post_service.dto.response;

import java.util.List;

import com.linkedsphere.post_service.entity.PostSearchDocument;
import com.linkedsphere.post_service.entity.UserSearchDocument;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnifiedSearchResponse {

    private String query;

    private List<UserSearchDocument> people;

    private List<PostSearchDocument> posts;

    private List<String> hashtags;

    private long totalPeople;

    private long totalPosts;
}
