package com.linksphere.user_service.service;

import com.linksphere.user_service.dto.request.CreateUserProfileRequest;
import com.linksphere.user_service.dto.request.UpdateUserProfileRequest;
import com.linksphere.user_service.dto.response.CreateUserProfileResponse;
import com.linksphere.user_service.security.user.AuthenticatedUser;

public interface UserProfileService {

    CreateUserProfileResponse createUserProfile(CreateUserProfileRequest request, AuthenticatedUser user);

    CreateUserProfileResponse updateMyProfile(UpdateUserProfileRequest request, AuthenticatedUser user);

    CreateUserProfileResponse getMyProfile(AuthenticatedUser user, String authHeader);

    CreateUserProfileResponse getUserProfileById(String userId);

}
