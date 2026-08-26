package com.linksphere.user_service.service.impl;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.linksphere.common.enums.ErrorCode;
import com.linksphere.common.exception.BaseException;
import com.linksphere.common.response.CurrentUserResponse;
import com.linksphere.user_service.client.AuthServiceClient;
import com.linksphere.user_service.dto.request.CreateUserProfileRequest;
import com.linksphere.user_service.dto.request.UpdateUserProfileRequest;
import com.linksphere.user_service.dto.response.CreateUserProfileResponse;
import com.linksphere.user_service.entity.UserProfileEntity;
import com.linksphere.user_service.repository.UserProfileRepository;
import com.linksphere.user_service.security.user.AuthenticatedUser;
import com.linksphere.user_service.service.UserProfileService;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final AuthServiceClient authServiceClient;

    @Override
    public CreateUserProfileResponse createUserProfile(CreateUserProfileRequest request, AuthenticatedUser user) {

        UUID authId = user.getUserId();

        if (authId == null) {
            log.error("Auth ID is null");
            throw new BaseException(ErrorCode.ACCESS_DENIED);
        }

        Optional<UserProfileEntity> userProfile = userProfileRepository
                .findByAuthIdAndIsDeletedFalse(authId);

        if (userProfile.isPresent()) {
            log.error("Profile already exists for the given Id");
            throw new BaseException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        UserProfileEntity userProfileEntity = UserProfileEntity.builder()
                .authId(authId)
                .fullName(request.getFullName())
                .headline(request.getHeadline())
                .about(request.getAbout())
                .profilePictureUrl(request.getProfilePictureUrl())
                .backgroundImageUrl(request.getBackgroundImageUrl())
                .location(request.getLocation())
                .industry(request.getIndustry())
                .websiteUrl(request.getWebsiteUrl())
                .isDeleted(false)
                .build();

        userProfileEntity.setCreatedAt(Instant.now());
        userProfileEntity.setUpdatedAt(Instant.now());

        UserProfileEntity savedUserProfile = userProfileRepository.save(userProfileEntity);

        if (savedUserProfile == null) {
            log.error("Failed to save user profile");
            throw new BaseException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        return CreateUserProfileResponse.builder()
                .id(savedUserProfile.getId().toString())
                .authId(savedUserProfile.getAuthId().toString())
                .fullName(savedUserProfile.getFullName())
                .headline(savedUserProfile.getHeadline())
                .about(savedUserProfile.getAbout())
                .profilePictureUrl(savedUserProfile.getProfilePictureUrl())
                .backgroundImageUrl(savedUserProfile.getBackgroundImageUrl())
                .location(savedUserProfile.getLocation())
                .industry(savedUserProfile.getIndustry())
                .websiteUrl(savedUserProfile.getWebsiteUrl())
                .build();

    }

    @Override
    public CreateUserProfileResponse updateMyProfile(UpdateUserProfileRequest request, AuthenticatedUser user) {
        UUID authId = user.getUserId();

        if (authId == null) {
            log.error("Auth ID is null");
            throw new BaseException(ErrorCode.ACCESS_DENIED);
        }

        Optional<UserProfileEntity> userProfile = userProfileRepository
                .findByIdAndIsDeletedFalse(UUID.fromString(request.getUserId()));

        if (!userProfile.isPresent()) {
            log.error("Profile not found for the given Id");
            throw new BaseException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        if (!userProfile.get().getAuthId().equals(authId)) {
            log.error("You are not authorized to update this profile");
            throw new BaseException(ErrorCode.ACCESS_DENIED);
        }

        UserProfileEntity userProfileEntity = userProfile.get();

        if (request.getFullName() != null) {
            userProfileEntity.setFullName(request.getFullName());
        }

        if (request.getHeadline() != null) {
            userProfileEntity.setHeadline(request.getHeadline());
        }

        if (request.getAbout() != null) {
            userProfileEntity.setAbout(request.getAbout());
        }

        if (request.getProfilePictureUrl() != null) {
            userProfileEntity.setProfilePictureUrl(request.getProfilePictureUrl());
        }

        if (request.getBackgroundImageUrl() != null) {
            userProfileEntity.setBackgroundImageUrl(request.getBackgroundImageUrl());
        }

        if (request.getLocation() != null) {
            userProfileEntity.setLocation(request.getLocation());
        }

        if (request.getIndustry() != null) {
            userProfileEntity.setIndustry(request.getIndustry());
        }

        if (request.getWebsiteUrl() != null) {
            userProfileEntity.setWebsiteUrl(request.getWebsiteUrl());
        }

        userProfileEntity.setUpdatedAt(Instant.now());

        UserProfileEntity savedUserProfile = userProfileRepository.save(userProfileEntity);

        if (savedUserProfile == null) {
            log.error("Failed to save user profile");
            throw new BaseException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        return CreateUserProfileResponse.builder()
                .id(savedUserProfile.getId().toString())
                .authId(savedUserProfile.getAuthId().toString())
                .fullName(savedUserProfile.getFullName())
                .headline(savedUserProfile.getHeadline())
                .about(savedUserProfile.getAbout())
                .profilePictureUrl(savedUserProfile.getProfilePictureUrl())
                .backgroundImageUrl(savedUserProfile.getBackgroundImageUrl())
                .location(savedUserProfile.getLocation())
                .industry(savedUserProfile.getIndustry())
                .websiteUrl(savedUserProfile.getWebsiteUrl())
                .build();

    }

    @Override
    public CreateUserProfileResponse getMyProfile(AuthenticatedUser user, String authHeader) {
        UUID authId = user.getUserId();

        if (authId == null) {
            log.error("Auth ID is null");
            throw new BaseException(ErrorCode.ACCESS_DENIED);
        }

        Optional<UserProfileEntity> userProfile = userProfileRepository
                .findByAuthIdAndIsDeletedFalse(authId);

        if (!userProfile.isPresent()) {
            log.error("Profile not found for the given Id");
            throw new BaseException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        CurrentUserResponse currentUser;
        try {
            currentUser = getCurrentUser();
        } catch (Exception e) {
            log.error("Failed to get current user profile", e);
            throw new BaseException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return CreateUserProfileResponse.builder()
                .id(userProfile.get().getId().toString())
                .authId(userProfile.get().getAuthId().toString())
                .fullName(userProfile.get().getFullName())
                .headline(userProfile.get().getHeadline())
                .about(userProfile.get().getAbout())
                .profilePictureUrl(userProfile.get().getProfilePictureUrl())
                .backgroundImageUrl(userProfile.get().getBackgroundImageUrl())
                .location(userProfile.get().getLocation())
                .industry(userProfile.get().getIndustry())
                .websiteUrl(userProfile.get().getWebsiteUrl())
                .email(currentUser.email())
                .phoneNumber(currentUser.phoneNumber())
                .roles(currentUser.roles())
                .build();
    }

    @Retry(name = "getCurrentUser", fallbackMethod = "getCurrentUserFallback")
    private CurrentUserResponse getCurrentUser() throws Exception {
        CurrentUserResponse currentUser = null;
        try {
            currentUser = authServiceClient.getCurrentUser();
        } catch (Exception exception) {
            log.error("Failed to get user profile", exception);
            throw new BaseException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return currentUser;
    }

    @Override
    public CreateUserProfileResponse getUserProfileById(String userId) {

        if (userId == null) {
            log.error("User ID is null");
            throw new BaseException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        Optional<UserProfileEntity> userProfile = userProfileRepository
                .findByIdAndIsDeletedFalse(UUID.fromString(userId));

        if (!userProfile.isPresent()) {
            log.error("Profile not found for the given Id");
            throw new BaseException(ErrorCode.RESOURCE_NOT_FOUND);
        }

        return CreateUserProfileResponse.builder()
                .id(userProfile.get().getId().toString())
                .authId(userProfile.get().getAuthId().toString())
                .fullName(userProfile.get().getFullName())
                .headline(userProfile.get().getHeadline())
                .about(userProfile.get().getAbout())
                .profilePictureUrl(userProfile.get().getProfilePictureUrl())
                .backgroundImageUrl(userProfile.get().getBackgroundImageUrl())
                .location(userProfile.get().getLocation())
                .industry(userProfile.get().getIndustry())
                .websiteUrl(userProfile.get().getWebsiteUrl())
                .build();
    }

}
