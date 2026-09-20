package com.linkedsphere.chat_service.dto.response;

import java.util.List;

public record PaginatedMessageResponse(
        List<MessageResponse> messages,
        String nextCursor,
        boolean hasMore) {

}
