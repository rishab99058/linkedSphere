package com.linksphere.user_service.dto.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserEducationResponse {

    private String id;
    private String userId;
    private String schoolName;
    private String degreeName;
    private String fieldOfStudy;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private String grade;
    private String description;
    private Boolean isCurrent;

}
