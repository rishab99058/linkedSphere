package com.linksphere.user_service.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.linksphere.common.enums.ErrorCode;
import com.linksphere.common.exception.BaseException;
import com.linksphere.user_service.dto.request.UserEducationRequest;
import com.linksphere.user_service.dto.response.UserEducationResponse;
import com.linksphere.user_service.entity.UserEducation;
import com.linksphere.user_service.repository.UserEducationDocummentRepository;
import com.linksphere.user_service.service.UserEducationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserEducationServiceImpl implements UserEducationService {

    private final UserEducationDocummentRepository userEducationDocummentRepository;

    @Override
    public UserEducationResponse createEducation(UserEducationRequest request) {

        UserEducation userEducation = UserEducation.builder()
                .schoolName(request.getSchoolName())
                .degreeName(request.getDegreeName())
                .fieldOfStudy(request.getFieldOfStudy())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .grade(request.getGrade())
                .description(request.getDescription())
                .isCurrent(request.getIsCurrent() != null && request.getIsCurrent())
                .userId(UUID.fromString(request.getUserId()))
                .build();

        userEducation.setCreatedAt(Instant.now());
        userEducation.setUpdatedAt(Instant.now());
        userEducation.setIsDeleted(false);

        userEducationDocummentRepository.save(userEducation);

        return UserEducationResponse.builder()
                .id(userEducation.getId().toString())
                .schoolName(userEducation.getSchoolName())
                .degreeName(userEducation.getDegreeName())
                .fieldOfStudy(userEducation.getFieldOfStudy())
                .startDate(userEducation.getStartDate())
                .endDate(userEducation.getEndDate())
                .grade(userEducation.getGrade())
                .description(userEducation.getDescription())
                .isCurrent(userEducation.getIsCurrent() != null && userEducation.getIsCurrent())
                .userId(userEducation.getUserId().toString())
                .build();
    }

    @Override
    public UserEducationResponse updateEducation(UserEducationRequest request) {
        Optional<UserEducation> educationOptional = userEducationDocummentRepository
                .findByIdAndIsDeletedFalse(UUID.fromString(request.getId()));
        if (educationOptional.isEmpty()) {
            log.error("Education not found for user_id = {}", request.getUserId());
            throw new BaseException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        UserEducation education = educationOptional.get();
        education.setSchoolName(request.getSchoolName());
        education.setDegreeName(request.getDegreeName());
        education.setFieldOfStudy(request.getFieldOfStudy());
        education.setStartDate(request.getStartDate());
        education.setEndDate(request.getEndDate());
        education.setGrade(request.getGrade());
        education.setDescription(request.getDescription());
        education.setIsCurrent(request.getIsCurrent() != null && request.getIsCurrent());
        education.setUpdatedAt(Instant.now());

        userEducationDocummentRepository.save(education);
        return UserEducationResponse.builder()
                .id(education.getId().toString())
                .schoolName(education.getSchoolName())
                .degreeName(education.getDegreeName())
                .fieldOfStudy(education.getFieldOfStudy())
                .startDate(education.getStartDate())
                .endDate(education.getEndDate())
                .grade(education.getGrade())
                .description(education.getDescription())
                .isCurrent(education.getIsCurrent() != null && education.getIsCurrent())
                .userId(education.getUserId().toString())
                .build();
    }

    @Override
    public void deleteEducation(String id) {
        Optional<UserEducation> educationOptional = userEducationDocummentRepository
                .findByIdAndIsDeletedFalse(UUID.fromString(id));
        if (educationOptional.isEmpty()) {
            log.error("Education not found for id = {}", id);
            throw new BaseException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        UserEducation education = educationOptional.get();
        education.setIsDeleted(true);
        education.setUpdatedAt(Instant.now());
        userEducationDocummentRepository.save(education);
    }

    @Override
    public UserEducationResponse getEducation(String id) {
        Optional<UserEducation> educationOptional = userEducationDocummentRepository
                .findByIdAndIsDeletedFalse(UUID.fromString(id));
        if (educationOptional.isEmpty()) {
            log.error("Education not found for id = {}", id);
            throw new BaseException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        UserEducation education = educationOptional.get();
        return UserEducationResponse.builder()
                .id(education.getId().toString())
                .schoolName(education.getSchoolName())
                .degreeName(education.getDegreeName())
                .fieldOfStudy(education.getFieldOfStudy())
                .startDate(education.getStartDate())
                .endDate(education.getEndDate())
                .grade(education.getGrade())
                .description(education.getDescription())
                .isCurrent(education.getIsCurrent() != null && education.getIsCurrent())
                .userId(education.getUserId().toString())
                .build();
    }

    @Override
    public List<UserEducationResponse> getEducations(String userId) {
        List<UserEducation> educations = userEducationDocummentRepository
                .findByUserIdAndIsDeletedFalse(UUID.fromString(userId));
        if (educations == null) {
            log.error("Education not found for user_id = {}", userId);
            throw new BaseException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        return educations.stream().map(education -> UserEducationResponse.builder()
                .id(education.getId().toString())
                .schoolName(education.getSchoolName())
                .degreeName(education.getDegreeName())
                .fieldOfStudy(education.getFieldOfStudy())
                .startDate(education.getStartDate())
                .endDate(education.getEndDate())
                .grade(education.getGrade())
                .description(education.getDescription())
                .isCurrent(education.getIsCurrent() != null && education.getIsCurrent())
                .userId(education.getUserId().toString())
                .build()).collect(Collectors.toList());
    }

}
