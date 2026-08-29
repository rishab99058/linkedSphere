package com.linksphere.auth_service.repository;

import com.linksphere.auth_service.entity.PasswordResetOtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PasswordResetOtpEntityRepository extends JpaRepository<PasswordResetOtpEntity, UUID> {

    Optional<PasswordResetOtpEntity> findTopByAuthIdAndVerifiedFalseAndUsedAtIsNullOrderByCreatedAtDesc(
            UUID authId);

    void deleteByAuthId(UUID authId);

    Optional<PasswordResetOtpEntity> findByOtpHashAndVerifiedFalseAndUsedAtIsNullOrderByCreatedAtDesc(String otpHash);

}
