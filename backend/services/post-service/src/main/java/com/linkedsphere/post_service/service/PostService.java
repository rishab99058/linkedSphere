package com.linkedsphere.post_service.service;

import com.linkedsphere.post_service.dto.request.CreatePostRequest;
import com.linkedsphere.post_service.dto.response.PostResponse;
import com.linkedsphere.post_service.security.user.AuthenticatedUser;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    PostResponse createPost(CreatePostRequest req, List<MultipartFile> media, AuthenticatedUser user);

}
