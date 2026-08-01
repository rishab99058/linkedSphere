package com.linksphere.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import com.linksphere.auth_service.entity.RoleEntity;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, UUID> {
    
    Optional<RoleEntity> findByName(String name);
    
}
