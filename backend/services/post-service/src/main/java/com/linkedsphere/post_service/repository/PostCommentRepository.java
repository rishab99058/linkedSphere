package com.linkedsphere.post_service.repository;

import com.linkedsphere.post_service.entity.PostComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCommentRepository extends MongoRepository<PostComment, String> {

    Page<PostComment> findByPostIdOrderByCreatedAtDesc(
            String postId,
            Pageable pageable);

    long countByPostId(String postId);

    Page<PostComment> findByUserIdOrderByCreatedAtDesc(
            String userId,
            Pageable pageable);
}