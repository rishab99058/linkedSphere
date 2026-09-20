package com.linkedsphere.post_service.service;

import java.util.List;

import com.linkedsphere.post_service.dto.request.CreateCommentRequest;
import com.linkedsphere.post_service.dto.response.PostCommentResponse;
import com.linkedsphere.post_service.security.user.AuthenticatedUser;

public interface PostCommentService {

    PostCommentResponse createComment(CreateCommentRequest request, AuthenticatedUser user, String authHeader);

    List<PostCommentResponse> getCommentsByPostId(String postId, String authHeader, int page, int size);

    void deleteComment(String commentId, AuthenticatedUser user);
}
