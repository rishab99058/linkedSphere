package com.linksphere.user_service.dto.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserExperienceRequest {
    private String id;
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Company name is required")
    private String companyName;
    @NotBlank(message = "Location is required")
    private String location;
    @NotNull(message = "Start date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    @NotBlank(message = "Description is required")
    private String description;

    private Boolean isCurrent;

    private String userId;
}
