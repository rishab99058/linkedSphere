package com.linksphere.user_service.dto.response;

import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateUserProfileResponse {

    private String id;
    private String authId;
    private String fullName;
    private String headline;
    private String about;
    private String profilePictureUrl;
    private String backgroundImageUrl;
    private String location;
    private String industry;
    private String websiteUrl;
    private String email;
    private String phoneNumber;
    private Set<String> roles;
}
