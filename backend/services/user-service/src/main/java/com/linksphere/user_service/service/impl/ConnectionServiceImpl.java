package com.linksphere.user_service.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.linksphere.common.enums.ErrorCode;
import com.linksphere.common.exception.BaseException;
import com.linksphere.user_service.dto.request.ConnectionRequest;
import com.linksphere.user_service.dto.response.ConnectionResponse;
import com.linksphere.user_service.dto.response.UserBasicResponse;
import com.linksphere.user_service.entity.ConnectionEntity;
import com.linksphere.user_service.enums.ConnectionStatus;
import com.linksphere.user_service.repository.ConnectionRepository;
import com.linksphere.user_service.repository.UserProfileRepository;
import com.linksphere.user_service.repository.projection.UserBasicProjection;
import com.linksphere.user_service.service.ConnectionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionServiceImpl implements ConnectionService {

    private final ConnectionRepository connectionRepository;
    private final UserProfileRepository userProfileRepository;

    @Override
    public ConnectionResponse sendConnectionRequest(ConnectionRequest request) {
        log.info("Sending connection request from {} to {}", request.getSenderId(), request.getReceiverId());

        if (request.getSenderId().equals(request.getReceiverId())) {
            log.error("Sender and receiver are same");
            throw new BaseException(ErrorCode.SELF_CONNECTION_REQUEST);
        }

        var existingConnection = connectionRepository.findByRequesterIdAndReceiverId(
                request.getSenderId(),
                request.getReceiverId());

        if (existingConnection.isPresent()) {
            log.error("Already connected request is sent to user with id " + request.getReceiverId());
            throw new BaseException(ErrorCode.ALREADY_CONNECTED);
        }

        var reverseConnection = connectionRepository.findByRequesterIdAndReceiverId(
                request.getReceiverId(),
                request.getSenderId());

        if (reverseConnection.isPresent()) {
            ConnectionEntity connection = reverseConnection.get();
            if (connection.getStatus() == ConnectionStatus.ACCEPTED) {
                log.error("Already connected request is accepted by you ");
                throw new BaseException(ErrorCode.ALREADY_CONNECTED);
            }

            if (connection.getStatus() == ConnectionStatus.PENDING) {
                log.error("Already connected request is pending for you ");
                throw new BaseException(ErrorCode.ALREADY_CONNECTED);
            }
        }

        var connection = ConnectionEntity.builder()
                .requesterId(request.getSenderId())
                .receiverId(request.getReceiverId())
                .status(ConnectionStatus.PENDING)
                .build();

        connectionRepository.save(connection);

        // need to send notification using rabbitmq

        return ConnectionResponse.builder()
                .connectionId(connection.getId())
                .requesterId(connection.getRequesterId())
                .receiverId(connection.getReceiverId())
                .status(connection.getStatus())
                .createdAt(connection.getCreatedAt())
                .build();
    }

    @Override
    public ConnectionResponse acceptRejectConnectionRequest(ConnectionRequest request) {
        ConnectionEntity connection = connectionRepository.findById(request.getConnectionId())
                .orElseThrow(() -> new BaseException(
                        ErrorCode.RESOURCE_NOT_FOUND));
        connection.setStatus(request.getStatus());
        connectionRepository.save(connection);
        return ConnectionResponse.builder()
                .connectionId(connection.getId())
                .requesterId(connection.getRequesterId())
                .receiverId(connection.getReceiverId())
                .status(connection.getStatus())
                .createdAt(connection.getCreatedAt())
                .build();
    }

    @Override
    public ConnectionResponse cancelConnectionRequest(ConnectionRequest request) {
        ConnectionEntity connection = connectionRepository.findById(request.getConnectionId())
                .orElseThrow(() -> new BaseException(
                        ErrorCode.RESOURCE_NOT_FOUND));
        connection.setStatus(ConnectionStatus.CANCELLED);
        connectionRepository.save(connection);
        return ConnectionResponse.builder()
                .connectionId(connection.getId())
                .requesterId(connection.getRequesterId())
                .receiverId(connection.getReceiverId())
                .status(connection.getStatus())
                .createdAt(connection.getCreatedAt())
                .build();
    }

    @Override
    public List<ConnectionResponse> getReceivedConnectionRequests(UUID receiverId, Pageable pageable) {
        Page<ConnectionEntity> connections = connectionRepository.findByReceiverIdAndStatus(
                receiverId,
                ConnectionStatus.PENDING,
                pageable);

        if (connections.isEmpty()) {
            return new ArrayList<>();
        }

        List<UUID> userIds = connections.getContent()
                .stream()
                .map(ConnectionEntity::getRequesterId)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        if (userIds.isEmpty()) {
            return new ArrayList<>();
        }

        List<UserBasicProjection> users = userProfileRepository.findBasicUsersByIds(userIds);

        Map<UUID, UserBasicProjection> userMap = users.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(
                        UserBasicProjection::getUserId,
                        user -> user,
                        (existing, replacement) -> existing));

        return connections.map(connection -> {

            UUID requesterId = connection.getRequesterId();

            UserBasicProjection user = userMap.get(requesterId);

            if (user == null) {
                throw new IllegalStateException(
                        "User profile not found for userId: " + requesterId);
            }

            UserBasicResponse userResponse = UserBasicResponse.builder()
                    .userId(user.getUserId())
                    .name(user.getFullName())
                    .profileImage(user.getProfilePictureUrl())
                    .headline(user.getHeadline())
                    .location(user.getLocation())
                    .build();

            return ConnectionResponse.builder()
                    .connectionId(connection.getId())
                    .user(userResponse)
                    .status(connection.getStatus())
                    .createdAt(connection.getCreatedAt())
                    .build();
        }).toList();

    }

}
