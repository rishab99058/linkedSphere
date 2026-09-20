package com.linkedsphere.post_service.service;

import com.linkedsphere.post_service.dto.response.PostLikeResponse;

public interface PostLikeService {

    PostLikeResponse likePost(String postId, String userId);

    PostLikeResponse unlikePost(String postId, String userId);

    PostLikeResponse getPostLikes(String postId, String userId);

}
