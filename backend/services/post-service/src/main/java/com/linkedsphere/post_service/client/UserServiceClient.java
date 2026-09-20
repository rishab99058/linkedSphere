package com.linkedsphere.post_service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.linkedsphere.post_service.dto.response.UserProfileClientResponse;
import com.linksphere.common.response.UserSummaryResponse;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @GetMapping("/api/v1/users/get_profile")
    UserProfileClientResponse getById(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam("user_id") String userId);

    @PostMapping("/api/v1/users/batch")
    List<UserSummaryResponse> getBasicUsersByIds(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody List<String> userIds);

    @GetMapping("/api/v1/connections/user-ids")
    List<String> getConnectedUserIds(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam("userId") String userId);
}
