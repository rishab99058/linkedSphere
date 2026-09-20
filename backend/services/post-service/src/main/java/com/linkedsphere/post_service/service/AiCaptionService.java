package com.linkedsphere.post_service.service;

import com.linkedsphere.post_service.dto.request.GenerateCaptionRequest;
import com.linkedsphere.post_service.dto.response.GenerateCaptionResponse;
import com.linkedsphere.post_service.security.user.AuthenticatedUser;

public interface AiCaptionService {

    GenerateCaptionResponse generateCaption(GenerateCaptionRequest request, AuthenticatedUser user);

    void handleN8nResponse(String responseJson);
}
