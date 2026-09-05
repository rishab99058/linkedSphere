package com.linksphere.user_service.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.linksphere.user_service.entity.UserProfileEntity;
import com.linksphere.user_service.repository.projection.UserBasicProjection;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfileEntity, UUID> {

    Optional<UserProfileEntity> findByAuthIdAndIsDeletedFalse(UUID authId);

    Optional<UserProfileEntity> findByIdAndIsDeletedFalse(UUID id);

    @Query("""
                SELECT
                    p.id AS userId,
                    p.fullName AS fullName,
                    p.profilePictureUrl AS profilePictureUrl,
                    p.headline AS headline,
                    p.location AS location
                FROM UserProfileEntity p
                WHERE p.id = :userId
                  AND p.isDeleted = false
            """)
    Optional<UserBasicProjection> findBasicUserById(
            @Param("userId") UUID userId);

    @Query("""
                SELECT
                    p.id AS userId,
                    p.fullName AS fullName,
                    p.profilePictureUrl AS profilePictureUrl,
                    p.headline AS headline,
                    p.location AS location
                FROM UserProfileEntity p
                WHERE p.id IN :userIds
                  AND p.isDeleted = false
            """)
    List<UserBasicProjection> findBasicUsersByIds(
            @Param("userIds") List<UUID> userIds);

}
