package com.linkedsphere.chat_service.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linkedsphere.chat_service.dto.request.MessageCursor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageCursorUtil {

    private final ObjectMapper objectMapper;

    public String encode(MessageCursor cursor) {

        try {

            String json = objectMapper.writeValueAsString(cursor);

            return Base64.getUrlEncoder()
                    .withoutPadding()
                    .encodeToString(
                            json.getBytes(StandardCharsets.UTF_8));

        } catch (JsonProcessingException e) {

            throw new IllegalStateException(
                    "Failed to encode message cursor",
                    e);
        }
    }

    public MessageCursor decode(String encodedCursor) {

        if (encodedCursor == null || encodedCursor.isBlank()) {
            return null;
        }

        try {

            byte[] decodedBytes = Base64.getUrlDecoder()
                    .decode(encodedCursor);

            String json = new String(
                    decodedBytes,
                    StandardCharsets.UTF_8);

            return objectMapper.readValue(
                    json,
                    MessageCursor.class);

        } catch (Exception e) {

            throw new IllegalArgumentException(
                    "Invalid message cursor",
                    e);
        }
    }
}