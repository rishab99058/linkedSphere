package com.linksphere.user_service.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.linksphere.user_service.entity.ConnectionEntity;
import com.linksphere.user_service.enums.ConnectionStatus;

@Repository
public interface ConnectionRepository extends JpaRepository<ConnectionEntity, UUID> {

    Optional<ConnectionEntity> findByRequesterIdAndReceiverId(
            UUID requesterId,
            UUID receiverId);

    Page<ConnectionEntity> findByReceiverIdAndStatus(
            UUID receiverId,
            ConnectionStatus status, Pageable pageable);

    Page<ConnectionEntity> findByRequesterIdAndStatus(
            UUID requesterId,
            ConnectionStatus status, Pageable pageable);

    List<ConnectionEntity> findByRequesterIdAndStatusOrReceiverIdAndStatus(
            UUID requesterId,
            ConnectionStatus requesterStatus,
            UUID receiverId,
            ConnectionStatus receiverStatus);

}
