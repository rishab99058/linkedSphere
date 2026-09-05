package com.linksphere.user_service.dto.response;

import com.linksphere.user_service.enums.ConnectionStatus;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionResponse {

    private UUID connectionId;
    private UUID requesterId;
    private UUID receiverId;
    private ConnectionStatus status;
    private Instant createdAt;
    private UserBasicResponse user;
}