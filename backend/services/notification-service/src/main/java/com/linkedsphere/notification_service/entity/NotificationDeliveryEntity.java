package com.linkedsphere.notification_service.entity;

import com.linkedsphere.notification_service.enums.NotificationDeliveryStatus;
import com.linksphere.common.enums.NotificationChannel;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "notification_deliveries", uniqueConstraints = {
        @UniqueConstraint(name = "uk_notification_channel", columnNames = { "notification_id", "channel" })
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDeliveryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "notification_id", nullable = false)
    private NotificationEntity notification;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NotificationChannel channel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NotificationDeliveryStatus status;

    @Column(nullable = false)
    private int retryCount;

    private Instant lastAttemptAt;

    private Instant deliveredAt;

    @Column(columnDefinition = "TEXT")
    private String failureReason;

}