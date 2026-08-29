package com.linkedsphere.notification_service.dto.request;

import com.linksphere.common.enums.NotificationChannel;
import com.linksphere.common.enums.NotificationType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public record NotificationRequest(

       @NotNull
       UUID recipientId,

       @NotNull
       NotificationType type,

       @NotBlank
       String title,

       @NotBlank
       String message,

       @NotEmpty
       Set<NotificationChannel> channels,

       Map<String, Object> metadata

) {
}
