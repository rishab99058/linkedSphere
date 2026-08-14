package com.linksphere.user_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.linksphere.user_service.dto.request.CreateUserProfileRequest;
import com.linksphere.user_service.dto.request.UpdateUserProfileRequest;
import com.linksphere.user_service.dto.response.CreateUserProfileResponse;
import com.linksphere.user_service.security.user.AuthenticatedUser;
import com.linksphere.user_service.service.UserProfileService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping("/create_my_profile")
    public ResponseEntity<CreateUserProfileResponse> createMyProfile(
            @Valid @RequestBody CreateUserProfileRequest request, @AuthenticationPrincipal AuthenticatedUser user) {
        CreateUserProfileResponse response = userProfileService.createUserProfile(request, user);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update_my_profile")
    public ResponseEntity<CreateUserProfileResponse> updateMyProfile(
            @Valid @RequestBody UpdateUserProfileRequest request, @AuthenticationPrincipal AuthenticatedUser user) {
        CreateUserProfileResponse response = userProfileService.updateMyProfile(request, user);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get_my_profile")
    public ResponseEntity<CreateUserProfileResponse> getMyProfile(
            @AuthenticationPrincipal AuthenticatedUser user) {
        CreateUserProfileResponse response = userProfileService.getMyProfile(user);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get_profile")
    public ResponseEntity<CreateUserProfileResponse> getProfile(@RequestParam("user_id") String userId) {
        CreateUserProfileResponse response = userProfileService.getUserProfileById(userId);
        return ResponseEntity.ok(response);
    }

}
