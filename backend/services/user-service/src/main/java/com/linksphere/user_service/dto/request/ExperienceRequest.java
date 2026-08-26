package com.linksphere.user_service.dto.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExperienceRequest {

    private String userId;

    private List<UserExperienceRequest> experiences;

}
