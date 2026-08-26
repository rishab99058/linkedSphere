package com.linksphere.user_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.linksphere.common.response.CurrentUserResponse;
import com.linksphere.user_service.config.FeignConfig;

@FeignClient(name = "auth-service", configuration = FeignConfig.class)
public interface AuthServiceClient {

    @GetMapping("auth/api/v1/profile/me")
    CurrentUserResponse getCurrentUser();
}
