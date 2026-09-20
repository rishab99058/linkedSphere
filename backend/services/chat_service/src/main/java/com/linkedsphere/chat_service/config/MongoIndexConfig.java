package com.linkedsphere.chat_service.config;

import org.springframework.context.annotation.Configuration;

import com.linkedsphere.chat_service.entity.ConversationMember;
import com.linkedsphere.chat_service.entity.Message;
import com.linkedsphere.chat_service.entity.MessageReaction;

import org.springframework.data.mongodb.core.MongoTemplate;

import jakarta.annotation.PostConstruct;
import org.bson.Document;

@Configuration
public class MongoIndexConfig {

    private final MongoTemplate mongoTemplate;

    public MongoIndexConfig(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @PostConstruct
    public void createIndexes() {

        // Messages
        mongoTemplate.getCollection(
                mongoTemplate.getCollectionName(Message.class)).createIndex(
                        new Document("conversationId", 1)
                                .append("createdAt", -1));

        // Conversation members
        mongoTemplate.getCollection(
                mongoTemplate.getCollectionName(ConversationMember.class)).createIndex(
                        new Document("conversationId", 1)
                                .append("userId", 1));

        // User conversation lookup
        mongoTemplate.getCollection(
                mongoTemplate.getCollectionName(ConversationMember.class)).createIndex(
                        new Document("userId", 1)
                                .append("updatedAt", -1));

        // Reactions
        mongoTemplate.getCollection(
                mongoTemplate.getCollectionName(MessageReaction.class)).createIndex(
                        new Document("messageId", 1)
                                .append("userId", 1)
                                .append("emoji", 1));
    }
}
