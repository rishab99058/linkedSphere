package com.linksphere.user_service.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.linksphere.user_service.entity.UserProfileEntity;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfileEntity, UUID> {

    Optional<UserProfileEntity> findByAuthIdAndIsDeletedFalse(UUID authId);

    Optional<UserProfileEntity> findByIdAndIsDeletedFalse(UUID id);

}
