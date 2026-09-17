package com.linkedsphere.chat_service.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.linkedsphere.chat_service.entity.Message;

@Repository
public interface MessageRepository
        extends MongoRepository<Message, String> {

    List<Message> findTop30ByConversationIdOrderByCreatedAtDesc(
            String conversationId);
}
