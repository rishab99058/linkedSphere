package com.linkedsphere.chat_service.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.linkedsphere.chat_service.dto.request.CreateMessageRequest;
import com.linkedsphere.chat_service.dto.request.GetMessagesRequest;
import com.linkedsphere.chat_service.dto.request.MessageCursor;
import com.linkedsphere.chat_service.dto.response.MessageResponse;
import com.linkedsphere.chat_service.dto.response.PaginatedMessageResponse;
import com.linkedsphere.chat_service.entity.Conversation;
import com.linkedsphere.chat_service.entity.ConversationMember;
import com.linkedsphere.chat_service.entity.Message;
import com.linkedsphere.chat_service.entity.MessageContent;
import com.linkedsphere.chat_service.enums.MessageType;
import com.linkedsphere.chat_service.repository.ConversationMemberRepository;
import com.linkedsphere.chat_service.repository.ConversationRepository;
import com.linkedsphere.chat_service.repository.MessageRepository;
import com.linkedsphere.chat_service.service.MessageService;
import com.linkedsphere.chat_service.util.MessageCursorUtil;
import com.linksphere.common.enums.ErrorCode;
import com.linksphere.common.exception.BaseException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final ConversationMemberRepository memberRepository;
    private final MongoTemplate mongoTemplate;
    private final MessageCursorUtil cursorUtil;

    @Override
    public PaginatedMessageResponse getMessages(String conversationId, String currentUserId,
            GetMessagesRequest request) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new BaseException(
                        ErrorCode.CONVERSATION_NOT_FOUND,
                        "Conversation not found"));

        ConversationMember member = memberRepository.findByConversationIdAndUserId(
                conversationId,
                currentUserId)
                .orElseThrow(() -> new BaseException(
                        ErrorCode.NOT_CONVERSATION_MEMBER,
                        "User is not a member of the conversation"));

        int limit = request.limit() == null
                ? 30
                : request.limit();

        Query query = new Query();
        query.addCriteria(
                Criteria.where("conversationId")
                        .is(conversationId));

        MessageCursor cursor = cursorUtil.decode(request.before());
        if (cursor != null) {
            Criteria olderMessages = new Criteria().orOperator(
                    Criteria.where("createdAt")
                            .lt(cursor.createdAt()),

                    new Criteria().andOperator(

                            Criteria.where("createdAt")
                                    .is(cursor.createdAt()),

                            Criteria.where("_id")
                                    .lt(cursor.id())));

            query.addCriteria(olderMessages);
        }
        query.with(
                Sort.by(
                        Sort.Order.desc("createdAt"),
                        Sort.Order.desc("_id")));

        query.limit(limit + 1);

        List<Message> fetchedMessages = mongoTemplate.find(
                query,
                Message.class);

        boolean hasMore = fetchedMessages.size() > limit;

        List<Message> pageMessages = hasMore
                ? fetchedMessages.subList(0, limit)
                : fetchedMessages;

        String nextCursor = null;

        if (hasMore && !pageMessages.isEmpty()) {

            Message lastMessage = pageMessages.get(
                    pageMessages.size() - 1);

            MessageCursor nextMessageCursor = new MessageCursor(
                    lastMessage.getCreatedAt(),
                    lastMessage.getId());

            nextCursor = cursorUtil.encode(
                    nextMessageCursor);
        }

        List<MessageResponse> responses = pageMessages.stream()
                .map(this::mapToResponse)
                .toList();

        return new PaginatedMessageResponse(
                responses,
                nextCursor,
                hasMore);

    }

    private MessageResponse mapToResponse(
            Message message) {

        return new MessageResponse(
                message.getId(),
                message.getConversationId(),
                message.getSenderId(),
                message.getType(),
                message.getContent(),
                message.getAttachments(),
                message.getReplyToMessageId(),
                message.isDeleted(),
                message.getCreatedAt(),
                message.getUpdatedAt());
    }

    @Override
    public MessageResponse createMessage(String conversationId, String currentUserId, CreateMessageRequest request) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new BaseException(
                        ErrorCode.CONVERSATION_NOT_FOUND,
                        "Conversation not found"));

        memberRepository
                .findByConversationIdAndUserId(
                        conversationId,
                        currentUserId)
                .orElseThrow(() -> new BaseException(
                        ErrorCode.NOT_CONVERSATION_MEMBER));

        validateMessage(request);

        Instant now = Instant.now();

        MessageContent content = MessageContent.builder()
                .text(request.text())
                .build();

        Message message = Message.builder()
                .conversationId(conversationId)
                .senderId(currentUserId)
                .type(request.type())
                .content(content)
                .attachments(request.attachments())
                .replyToMessageId(request.replyToMessageId())
                .deleted(false)
                .createdAt(now)
                .updatedAt(now)
                .build();

        // 5. Save message
        message = messageRepository.save(message);

        log.info(
                "Message created. messageId={}, conversationId={}, senderId={}",
                message.getId(),
                conversationId,
                currentUserId);

        return mapToResponse(message);

    }

    private void validateMessage(CreateMessageRequest request) {

        if (request.type() == MessageType.TEXT) {

            if (request.text() == null ||
                    request.text().isBlank()) {

                throw new BaseException(
                        ErrorCode.MESSAGE_CONTENT_REQUIRED);
            }
        }

        if (request.type() == MessageType.IMAGE ||
                request.type() == MessageType.VIDEO ||
                request.type() == MessageType.AUDIO ||
                request.type() == MessageType.DOCUMENT ||
                request.type() == MessageType.FILE) {

            if (request.attachments() == null ||
                    request.attachments().isEmpty()) {

                throw new BaseException(
                        ErrorCode.MESSAGE_ATTACHMENT_REQUIRED);
            }
        }
    }
}
