package com.linkedsphere.post_service.service;

import com.linkedsphere.post_service.dto.request.CreatePostRequest;
import com.linkedsphere.post_service.dto.request.UpdatePostRequest;
import com.linkedsphere.post_service.dto.response.PostResponse;
import com.linkedsphere.post_service.security.user.AuthenticatedUser;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    PostResponse createPost(CreatePostRequest req, List<MultipartFile> media, AuthenticatedUser user);

    PostResponse updatePost(UpdatePostRequest req, List<MultipartFile> media, AuthenticatedUser user);

    PostResponse getPostById(String postId, String authHeader);

    List<PostResponse> getPostsByUserId(String userId, String authHeader, int page, int size);

    String deletePost(String postId);

    List<PostResponse> getMyArchivedPosts(String userId, int page, int size);

    PostResponse archivePost(String postId);

    PostResponse unarchivePost(String postId);

    List<PostResponse> getFeed(AuthenticatedUser user, String authHeader, int page, int size);

}
