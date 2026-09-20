package com.linksphere.user_service.service;

import java.util.List;

import com.linksphere.common.response.UserSummaryResponse;
import com.linksphere.user_service.dto.request.CreateUserProfileRequest;
import com.linksphere.user_service.dto.request.UpdateUserProfileRequest;
import com.linksphere.user_service.dto.response.CreateUserProfileResponse;
import com.linksphere.user_service.security.user.AuthenticatedUser;

public interface UserProfileService {

    CreateUserProfileResponse createUserProfile(CreateUserProfileRequest request, AuthenticatedUser user);

    CreateUserProfileResponse updateMyProfile(UpdateUserProfileRequest request, AuthenticatedUser user);

    CreateUserProfileResponse getMyProfile(AuthenticatedUser user, String authHeader);

    CreateUserProfileResponse getUserProfileById(String userId);

    List<UserSummaryResponse> getBasicUsersByIds(List<String> userIds);

}
