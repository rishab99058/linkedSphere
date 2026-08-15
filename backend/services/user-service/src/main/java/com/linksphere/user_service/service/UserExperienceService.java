package com.linksphere.user_service.service;

import java.util.List;

import com.linksphere.user_service.dto.request.UserExperienceRequest;
import com.linksphere.user_service.dto.response.UserExperienceResponse;

public interface UserExperienceService {

    UserExperienceResponse createExperience(UserExperienceRequest request);

    UserExperienceResponse updateExperience(UserExperienceRequest request);

    void deleteExperience(String id);

    UserExperienceResponse getExperience(String id);

    List<UserExperienceResponse> getExperiences(String userId);

}
