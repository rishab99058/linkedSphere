package com.linksphere.user_service.service;

import java.util.List;

import com.linksphere.user_service.dto.request.UserEducationRequest;
import com.linksphere.user_service.dto.response.UserEducationResponse;

public interface UserEducationService {

    UserEducationResponse createEducation(UserEducationRequest request);

    UserEducationResponse updateEducation(UserEducationRequest request);

    void deleteEducation(String id);

    UserEducationResponse getEducation(String id);

    List<UserEducationResponse> getEducations(String userId);

}
