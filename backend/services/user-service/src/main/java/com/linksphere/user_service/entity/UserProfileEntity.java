package com.linksphere.user_service.entity;

import java.util.UUID;

import com.linksphere.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "user_profiles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "auth_id", unique = true, nullable = false)
    private UUID authId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "headline")
    private String headline;

    @Column(name = "about", columnDefinition = "TEXT")
    private String about;

    @Column(name = "profile_picture_url")
    private String profilePictureUrl;

    @Column(name = "background_image_url")
    private String backgroundImageUrl;

    @Column(name = "location")
    private String location;

    @Column(name = "industry")
    private String industry;

    @Column(name = "website_url")
    private String websiteUrl;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

}
