package com.linkedsphere.chat_service.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record GetMessagesRequest(

        String before,

        @Min(1) @Max(100) Integer limit

) {
}
