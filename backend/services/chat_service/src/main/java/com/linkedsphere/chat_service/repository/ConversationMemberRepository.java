package com.linkedsphere.chat_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.linkedsphere.chat_service.entity.ConversationMember;

import java.util.List;
import java.util.Optional;

public interface ConversationMemberRepository
        extends MongoRepository<ConversationMember, String> {

    List<ConversationMember> findByConversationId(
            String conversationId);

    List<ConversationMember> findByUserId(
            String userId);

    Optional<ConversationMember> findByConversationIdAndUserId(
            String conversationId,
            String userId);

    boolean existsByConversationIdAndUserId(
            String conversationId,
            String userId);
}
