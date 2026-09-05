package com.linksphere.user_service.entity;

import com.linksphere.common.entity.BaseEntity;
import com.linksphere.user_service.enums.ConnectionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "connections", indexes = {
        @Index(name = "idx_connection_requester", columnList = "requester_id"),
        @Index(name = "idx_connection_receiver", columnList = "receiver_id"),
        @Index(name = "idx_connection_status", columnList = "status")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "requester_id", nullable = false)
    private UUID requesterId;

    @Column(name = "receiver_id", nullable = false)
    private UUID receiverId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ConnectionStatus status;
}