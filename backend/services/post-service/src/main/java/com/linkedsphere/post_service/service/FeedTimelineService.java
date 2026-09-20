package com.linkedsphere.post_service.service;

import java.util.List;
import java.util.Set;

public interface FeedTimelineService {

    void fanOutPostToConnections(String postId, String authorId, long timestampEpochMilli);

    void removePostFromTimelines(String postId, String authorId);

    List<String> getTimelinePostIds(String userId, int page, int size);

    void populateUserTimeline(String userId, List<com.linkedsphere.post_service.entity.Post> posts);
}
