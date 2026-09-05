package com.linksphere.common.request;

import lombok.*;
import java.util.UUID;

import com.linksphere.common.enums.NotificationType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FCMNotificationRequest {

    private UUID recipientId;

    private UUID actorId;

    private NotificationType type;

    private String title;

    private String message;

    private NotificationData data;
}
