package com.linksphere.user_service.controller;

import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.linksphere.user_service.dto.request.UserExperienceRequest;
import com.linksphere.user_service.dto.response.UserExperienceResponse;
import com.linksphere.user_service.service.UserExperienceService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/experience")
public class UserExperienceController {

    private final UserExperienceService userExperienceService;

    @PostMapping("/create")
    public ResponseEntity<UserExperienceResponse> createUserExperience(
            @RequestBody @Valid UserExperienceRequest request) {
        UserExperienceResponse response = userExperienceService.createExperience(request);
        return ResponseEntity.status(HttpStatus.SC_CREATED).body(response);
    }

    @GetMapping("/get")
    public ResponseEntity<UserExperienceResponse> getUserExperienceById(@RequestParam("id") String id) {
        UserExperienceResponse response = userExperienceService.getExperience(id);
        return ResponseEntity.status(HttpStatus.SC_OK).body(response);
    }

    @PutMapping("/update")
    public ResponseEntity<UserExperienceResponse> updateUserExperience(
            @RequestBody @Valid UserExperienceRequest request) {
        UserExperienceResponse response = userExperienceService.updateExperience(request);
        return ResponseEntity.status(HttpStatus.SC_OK).body(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUserExperience(@RequestParam("id") String id) {
        userExperienceService.deleteExperience(id);
        return ResponseEntity.status(HttpStatus.SC_OK).body("User experience deleted successfully");
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserExperienceResponse>> getUserExperiencesByUserId(
            @RequestParam("user_id") String userId) {
        List<UserExperienceResponse> response = userExperienceService.getExperiences(userId);
        return ResponseEntity.status(HttpStatus.SC_OK).body(response);
    }

}
