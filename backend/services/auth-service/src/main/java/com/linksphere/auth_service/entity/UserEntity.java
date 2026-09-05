package com.linksphere.auth_service.entity;

import com.linksphere.auth_service.enums.AccountStatus;
import com.linksphere.auth_service.enums.AuthProvider;
import com.linksphere.common.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "uk_users_email", columnNames = "email"),
        @UniqueConstraint(name = "uk_users_phone_number", columnNames = "phone_number")
})
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private AuthProvider provider;

    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "email_verified", nullable = false)
    private Boolean emailVerified;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status", nullable = false, length = 20)
    private AccountStatus accountStatus;

    @Column(name = "is_deleted", nullable = false)
    private Boolean deleted;

    /**
     * Roles assigned to this user via the user_roles join table.
     * CascadeType.ALL ensures UserRole rows are inserted/deleted with the user.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Builder.Default
    private List<UserRole> userRoles = new ArrayList<>();
}
