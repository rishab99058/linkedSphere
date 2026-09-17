package com.linkedsphere.chat_service.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.linkedsphere.chat_service.entity.Conversation;
import com.linkedsphere.chat_service.enums.ConversationType;

@Repository
public interface ConversationRepository
        extends MongoRepository<Conversation, String> {

    Optional<Conversation> findByTypeAndCreatedBy(ConversationType type, String createdBy);

    Optional<Conversation> findByType(
            ConversationType type);
}
