package com.linkedsphere.chat_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.linkedsphere.chat_service.entity.MessageReaction;

@Repository
public interface MessageReactionRepository
        extends MongoRepository<MessageReaction, String> {

    List<MessageReaction> findByMessageId(String messageId);
}
