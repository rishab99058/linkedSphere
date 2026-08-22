package com.linkedsphere.notification_service.repository;

import com.linkedsphere.notification_service.entity.NotificationEntity;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationEntityRepository extends JpaRepository<NotificationEntity, UUID> {

    Page<NotificationEntity> findByRecipientIdOrderByCreatedAtDesc(
            UUID recipientId,
            Pageable pageable
    );

    Page<NotificationEntity> findByRecipientIdAndReadFalseOrderByCreatedAtDesc(
            UUID recipientId,
            Pageable pageable
    );

    long countByRecipientIdAndReadFalse(UUID recipientId);

}
