package com.linkedsphere.chat_service.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkedsphere.chat_service.dto.request.CreateConversationRequest;
import com.linkedsphere.chat_service.dto.response.ConversationMemberResponse;
import com.linkedsphere.chat_service.dto.response.ConversationResponse;
import com.linkedsphere.chat_service.entity.Conversation;
import com.linkedsphere.chat_service.entity.ConversationMember;
import com.linkedsphere.chat_service.enums.ConversationMemberRole;
import com.linkedsphere.chat_service.enums.ConversationMemberStatus;
import com.linkedsphere.chat_service.enums.ConversationType;
import com.linkedsphere.chat_service.repository.ConversationMemberRepository;
import com.linkedsphere.chat_service.repository.ConversationRepository;
import com.linkedsphere.chat_service.service.ConversationService;
import com.linksphere.common.enums.ErrorCode;
import com.linksphere.common.exception.BaseException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;
    private final ConversationMemberRepository memberRepository;

    @Override
    @Transactional
    public ConversationResponse createConversation(
            String currentUserId,
            CreateConversationRequest request) {

        validateParticipants(currentUserId, request);

        Set<String> participantIds = new HashSet<>(request.participantIds());

        participantIds.add(currentUserId);

        if (request.type() == ConversationType.DIRECT) {

            if (participantIds.size() != 2) {
                throw new BaseException(
                        ErrorCode.DIRECT_CONVERSATION_MEMBER_LIMIT);
            }

        }

        Instant now = Instant.now();

        Conversation conversation = Conversation.builder()
                .type(request.type())
                .name(request.name())
                .description(request.description())
                .avatarUrl(request.avatarUrl())
                .createdBy(currentUserId)
                .createdAt(now)
                .updatedAt(now)
                .build();

        conversation = conversationRepository.save(conversation);

        List<ConversationMember> members = new ArrayList<>();

        for (String userId : participantIds) {

            ConversationMemberRole role = userId.equals(currentUserId)
                    ? ConversationMemberRole.OWNER
                    : ConversationMemberRole.MEMBER;

            ConversationMember member = ConversationMember.builder()
                    .conversationId(conversation.getId())
                    .userId(userId)
                    .role(role)
                    .status(ConversationMemberStatus.ACTIVE)
                    .muted(false)
                    .archived(false)
                    .joinedAt(now)
                    .updatedAt(now)
                    .build();

            members.add(member);
        }

        memberRepository.saveAll(members);

        return mapToResponse(conversation, members);
    }

    @Override
    public ConversationResponse getConversation(
            String conversationId,
            String currentUserId) {

        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new BaseException(
                        ErrorCode.CONVERSATION_NOT_FOUND));

        boolean isMember = memberRepository
                .findByConversationIdAndUserId(
                        conversationId,
                        currentUserId)
                .isPresent();

        if (!isMember) {
            throw new BaseException(
                    ErrorCode.NOT_CONVERSATION_MEMBER);
        }

        List<ConversationMember> members = memberRepository.findByConversationId(
                conversationId);

        return mapToResponse(conversation, members);
    }

    @Override
    public List<ConversationResponse> getUserConversations(
            String currentUserId) {

        List<ConversationMember> memberships = memberRepository.findByUserId(currentUserId);

        return memberships.stream()
                .filter(member -> member.getStatus() == ConversationMemberStatus.ACTIVE)
                .map(member -> conversationRepository
                        .findById(member.getConversationId())
                        .orElse(null))
                .filter(conversation -> conversation != null)
                .map(conversation -> {

                    List<ConversationMember> members = memberRepository.findByConversationId(
                            conversation.getId());

                    return mapToResponse(
                            conversation,
                            members);
                })
                .toList();
    }

    private void validateParticipants(
            String currentUserId,
            CreateConversationRequest request) {

        if (request.participantIds() == null ||
                request.participantIds().isEmpty()) {

            throw new BaseException(
                    ErrorCode.AT_LEAST_ONE_PARTICIPANT_REQUIRED);
        }

        if (request.participantIds()
                .contains(currentUserId)) {

            throw new BaseException(
                    ErrorCode.CANNOT_INCLUDE_SELF_IN_PARTICIPANTS);
        }
    }

    private ConversationResponse mapToResponse(
            Conversation conversation,
            List<ConversationMember> members) {

        List<ConversationMemberResponse> memberResponses = members.stream()
                .map(member -> new ConversationMemberResponse(
                        member.getUserId(),
                        member.getRole(),
                        member.getStatus(),
                        member.getLastReadMessageId(),
                        member.getLastReadAt(),
                        member.isMuted(),
                        member.isArchived(),
                        member.getJoinedAt()))
                .toList();

        return new ConversationResponse(
                conversation.getId(),
                conversation.getType(),
                conversation.getName(),
                conversation.getDescription(),
                conversation.getAvatarUrl(),
                conversation.getCreatedBy(),
                conversation.getLastMessageId(),
                conversation.getLastMessagePreview(),
                conversation.getLastMessageAt(),
                conversation.getCreatedAt(),
                conversation.getUpdatedAt(),
                memberResponses);
    }

}
