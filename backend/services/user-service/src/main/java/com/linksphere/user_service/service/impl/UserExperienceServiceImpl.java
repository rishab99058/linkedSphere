package com.linksphere.user_service.service.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.linksphere.common.enums.ErrorCode;
import com.linksphere.common.exception.BaseException;
import com.linksphere.user_service.dto.request.UserExperienceRequest;
import com.linksphere.user_service.dto.response.UserExperienceResponse;
import com.linksphere.user_service.entity.UserExperiece;
import com.linksphere.user_service.repository.UserExperienceDocumentRepository;
import com.linksphere.user_service.service.UserExperienceService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserExperienceServiceImpl implements UserExperienceService {

    private final UserExperienceDocumentRepository userExperienceDocumentRepository;

    @Override
    public UserExperienceResponse createExperience(UserExperienceRequest request) {

        UserExperiece userExperience = UserExperiece.builder()
                .companyName(request.getCompanyName())
                .title(request.getTitle())
                .location(request.getLocation())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .description(request.getDescription())
                .isCurrent(request.getIsCurrent() != null && request.getIsCurrent() ? true : false)
                .userId(UUID.fromString(request.getUserId()))
                .build();

        userExperience.setCreatedAt(Instant.now());
        userExperience.setUpdatedAt(Instant.now());

        userExperience.setIsDeleted(false);

        userExperienceDocumentRepository.save(userExperience);

        return UserExperienceResponse.builder()
                .id(userExperience.getId().toString())
                .companyName(userExperience.getCompanyName())
                .title(userExperience.getTitle())
                .location(userExperience.getLocation())
                .startDate(userExperience.getStartDate())
                .endDate(userExperience.getEndDate())
                .description(userExperience.getDescription())
                .isCurrent(userExperience.getIsCurrent() != null && userExperience.getIsCurrent() ? true : false)
                .userId(userExperience.getUserId().toString())
                .build();

    }

    @Override
    public UserExperienceResponse updateExperience(UserExperienceRequest request) {
        // TODO Auto-generated method stub
        Optional<UserExperiece> experiece = userExperienceDocumentRepository
                .findByIdAndIsDeletedFalse(UUID.fromString(request.getId()));
        if (experiece.get() == null) {
            log.error("Experience not found for user_id = {}", request.getUserId());
            throw new BaseException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        experiece.get().setCompanyName(request.getCompanyName());
        experiece.get().setTitle(request.getTitle());
        experiece.get().setLocation(request.getLocation());
        experiece.get().setStartDate(request.getStartDate());
        experiece.get().setEndDate(request.getEndDate());
        experiece.get().setDescription(request.getDescription());
        experiece.get().setIsCurrent(request.getIsCurrent() != null && request.getIsCurrent() ? true : false);
        experiece.get().setUpdatedAt(Instant.now());

        userExperienceDocumentRepository.save(experiece.get());
        return UserExperienceResponse.builder()
                .id(experiece.get().getId().toString())
                .companyName(experiece.get().getCompanyName())
                .title(experiece.get().getTitle())
                .location(experiece.get().getLocation())
                .startDate(experiece.get().getStartDate())
                .endDate(experiece.get().getEndDate())
                .description(experiece.get().getDescription())
                .isCurrent(experiece.get().getIsCurrent() != null && experiece.get().getIsCurrent() ? true : false)
                .userId(experiece.get().getUserId().toString())
                .build();
    }

    @Override
    public void deleteExperience(String id) {
        // TODO Auto-generated method stub
        UserExperiece experiece = userExperienceDocumentRepository.findByIdAndIsDeletedFalse(UUID.fromString(id)).get();
        if (experiece == null) {
            log.error("Experience not found for id = {}", id);
            throw new BaseException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        experiece.setIsDeleted(true);
        experiece.setUpdatedAt(Instant.now());
        userExperienceDocumentRepository.save(experiece);
    }

    @Override
    public UserExperienceResponse getExperience(String id) {
        // TODO Auto-generated method stub
        UserExperiece experiece = userExperienceDocumentRepository.findByIdAndIsDeletedFalse(UUID.fromString(id)).get();
        if (experiece == null) {
            log.error("Experience not found for id = {}", id);
            throw new BaseException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        return UserExperienceResponse.builder()
                .id(experiece.getId().toString())
                .companyName(experiece.getCompanyName())
                .title(experiece.getTitle())
                .location(experiece.getLocation())
                .startDate(experiece.getStartDate())
                .endDate(experiece.getEndDate())
                .description(experiece.getDescription())
                .isCurrent(experiece.getIsCurrent() != null && experiece.getIsCurrent() ? true : false)
                .userId(experiece.getUserId().toString())
                .build();

    }

    @Override
    public List<UserExperienceResponse> getExperiences(String userId) {
        // TODO Auto-generated method stub
        List<UserExperiece> experieces = userExperienceDocumentRepository
                .findByUserIdAndIsDeletedFalse(UUID.fromString(userId));
        if (experieces == null) {
            log.error("Experience not found for user_id = {}", userId);
            throw new BaseException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        return experieces.stream().map(experiece -> UserExperienceResponse.builder()
                .id(experiece.getId().toString())
                .companyName(experiece.getCompanyName())
                .title(experiece.getTitle())
                .location(experiece.getLocation())
                .startDate(experiece.getStartDate())
                .endDate(experiece.getEndDate())
                .description(experiece.getDescription())
                .isCurrent(experiece.getIsCurrent() != null && experiece.getIsCurrent() ? true : false)
                .userId(experiece.getUserId().toString())
                .build()).collect(java.util.stream.Collectors.toList());
    }

}
