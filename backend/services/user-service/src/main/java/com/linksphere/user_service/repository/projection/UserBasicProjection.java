package com.linksphere.user_service.repository.projection;

import java.util.UUID;

public interface UserBasicProjection {

    UUID getUserId();

    String getFullName();

    String getProfilePictureUrl();

    String getHeadline();

    String getLocation();
}