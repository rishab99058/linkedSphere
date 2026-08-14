package com.linksphere.user_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserProfileRequest {

    private String authId;

    @NotBlank(message = "Full name is required")
    private String fullName;

    private String headline;

    private String about;

    private String profilePictureUrl;

    private String backgroundImageUrl;

    private String location;

    private String industry;

    private String websiteUrl;

}
