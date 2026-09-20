package com.linkedsphere.notification_service.dto.request;

import java.util.Map;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PushNotificationRequest {

    private String token;

    private String topic;

    @NotBlank(message = "Title must not be blank")
    private String title;

    @NotBlank(message = "Body must not be blank")
    private String body;

    private String imageUrl;

    private Map<String, String> data;
}
