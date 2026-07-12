package com.linksphere.auth_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "user_roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRole {

    @EmbeddedId
    private UserRoleId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    /**
     * Initialises the composite key and audit timestamp before insert.
     * The user ID is available here because JPA resolves @MapsId before @PrePersist.
     */
    @PrePersist
    private void prePersist() {
        this.id = new UserRoleId(user.getId(), role.getId());
        if (this.createdAt == null) {
            this.createdAt = Instant.now();
        }
    }
}