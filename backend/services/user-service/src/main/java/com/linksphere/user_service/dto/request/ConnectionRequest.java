package com.linksphere.user_service.dto.request;

import java.util.UUID;

import com.linksphere.user_service.enums.ConnectionStatus;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConnectionRequest {

    private UUID senderId;

    private UUID receiverId;

    private ConnectionStatus status;

    private UUID connectionId;

}
