package com.linkedsphere.post_service.repository;

import com.linkedsphere.post_service.entity.Post;
import com.linkedsphere.post_service.enums.PostStatus;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {

    Post findByIdAndStatusNot(String id, PostStatus status);

    Page<Post> findByAuthorIdAndStatusNot(String authorId, PostStatus status, Pageable pageable);

    Page<Post> findByAuthorIdAndStatus(String authorId, PostStatus status, Pageable pageable);

    Page<Post> findByAuthorIdInAndStatusOrderByCreatedAtDesc(java.util.List<String> authorIds, PostStatus status, Pageable pageable);

    Page<Post> findByStatusOrderByCreatedAtDesc(PostStatus status, Pageable pageable);

}
