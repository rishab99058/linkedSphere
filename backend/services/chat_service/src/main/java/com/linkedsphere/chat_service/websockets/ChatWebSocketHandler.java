package com.linkedsphere.chat_service.websockets;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final WebSocketConnectionManager webSocketConnectionManager;
    private final ObjectMapper objectMapper;

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userId = getUserId(session);

        if (userId != null) {
            webSocketConnectionManager.removeSession(userId);
        }

        System.out.println(
                "WebSocket disconnected: "
                        + userId);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = getUserId(session);
        if (userId == null || userId.isBlank()) {

            session.close(
                    CloseStatus.BAD_DATA);

            return;
        }

        webSocketConnectionManager.addSession(userId, session);
        session.sendMessage(
                new TextMessage("""
                        {
                          "type": "CONNECTED",
                          "message": "Connected to LinkedSPHERE Chat"
                        }
                        """));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String userId = getUserId(session);

        JsonNode request = objectMapper.readTree(message.getPayload());

        String type = request.path("type").asText();

        if ("SEND_MESSAGE".equals(type)) {

            String toUserId = request.path("toUserId").asText();
            String text = request.path("message").asText();

            WebSocketSession receiverSession = webSocketConnectionManager.getSession(toUserId);

            if (receiverSession == null || !receiverSession.isOpen()) {

                session.sendMessage(new TextMessage("""
                        {
                          "type": "USER_OFFLINE",
                          "message": "Receiver is offline"
                        }
                        """));

                return;
            }

            String response = objectMapper.writeValueAsString(
                    java.util.Map.of(
                            "type", "NEW_MESSAGE",
                            "fromUserId", userId,
                            "message", text));

            receiverSession.sendMessage(
                    new TextMessage(response));

            // ACK to sender
            session.sendMessage(new TextMessage("""
                    {
                      "type": "MESSAGE_SENT",
                      "message": "Message delivered to receiver"
                    }
                    """));
        }
    }

    private String getUserId(WebSocketSession session) {
        if (session.getUri() == null)
            return null;

        return UriComponentsBuilder.fromUri(session.getUri())
                .build()
                .getQueryParams()
                .getFirst("userId");
    }

}
