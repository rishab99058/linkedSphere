package com.linksphere.user_service.controller;

import java.util.List;

import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.linksphere.user_service.dto.request.UserEducationRequest;
import com.linksphere.user_service.dto.response.UserEducationResponse;
import com.linksphere.user_service.service.UserEducationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/education")
public class UserEducationController {

    private final UserEducationService userEducationService;

    @PostMapping("/create")
    public ResponseEntity<UserEducationResponse> createUserEducation(
            @RequestBody @Valid UserEducationRequest request) {
        UserEducationResponse response = userEducationService.createEducation(request);
        return ResponseEntity.status(HttpStatus.SC_CREATED).body(response);
    }

    @GetMapping("/get")
    public ResponseEntity<UserEducationResponse> getUserEducationById(@RequestParam("id") String id) {
        UserEducationResponse response = userEducationService.getEducation(id);
        return ResponseEntity.status(HttpStatus.SC_OK).body(response);
    }

    @PutMapping("/update")
    public ResponseEntity<UserEducationResponse> updateUserEducation(
            @RequestBody @Valid UserEducationRequest request) {
        UserEducationResponse response = userEducationService.updateEducation(request);
        return ResponseEntity.status(HttpStatus.SC_OK).body(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUserEducation(@RequestParam("id") String id) {
        userEducationService.deleteEducation(id);
        return ResponseEntity.status(HttpStatus.SC_OK).body("User education deleted successfully");
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserEducationResponse>> getUserEducationsByUserId(
            @RequestParam("user_id") String userId) {
        List<UserEducationResponse> response = userEducationService.getEducations(userId);
        return ResponseEntity.status(HttpStatus.SC_OK).body(response);
    }

}
