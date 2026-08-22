package com.linkedsphere.notification_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.linksphere.common.enums.NotificationChannel;
import com.linkedsphere.notification_service.enums.NotificationDeliveryStatus;
import java.util.Optional;

import com.linkedsphere.notification_service.entity.NotificationDeliveryEntity;

@Repository
public interface NotificationDelivaryRepository extends JpaRepository<NotificationDeliveryEntity, UUID> {

    Optional<NotificationDeliveryEntity> findByNotificationIdAndChannel(
            UUID notificationId,
            NotificationChannel channel
    );

    boolean existsByNotificationIdAndChannel(
            UUID notificationId,
            NotificationChannel channel
    );

    long countByNotificationIdAndStatus(
            UUID notificationId,
            NotificationDeliveryStatus status
    );
}
