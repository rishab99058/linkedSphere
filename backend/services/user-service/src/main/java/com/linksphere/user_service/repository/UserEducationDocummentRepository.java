package com.linksphere.user_service.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.linksphere.user_service.entity.UserEducation;

@Repository
public interface UserEducationDocummentRepository extends JpaRepository<UserEducation, UUID> {

    public List<UserEducation> findByUserIdAndIsDeletedFalse(UUID userId);

    public Optional<UserEducation> findByIdAndIsDeletedFalse(UUID id);

}
