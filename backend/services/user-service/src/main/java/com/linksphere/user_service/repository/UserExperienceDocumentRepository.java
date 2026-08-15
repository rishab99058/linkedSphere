package com.linksphere.user_service.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.linksphere.user_service.entity.UserExperiece;

@Repository
public interface UserExperienceDocumentRepository extends JpaRepository<UserExperiece, UUID> {

    public List<UserExperiece> findByUserIdAndIsDeletedFalse(UUID userId);

    public Optional<UserExperiece> findByIdAndIsDeletedFalse(UUID id);

}
