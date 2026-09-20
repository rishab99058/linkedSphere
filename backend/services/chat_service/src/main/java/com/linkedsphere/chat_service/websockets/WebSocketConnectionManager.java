package com.linkedsphere.chat_service.websockets;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WebSocketConnectionManager {

    private final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public void addSession(
            String userId,
            WebSocketSession session) {
        sessions.put(userId, session);

        System.out.println(
                "User connected: " + userId);
    }

    public void removeSession(String userId) {
        sessions.remove(userId);

        System.out.println(
                "User disconnected: " + userId);
    }

    public WebSocketSession getSession(String userId) {
        return sessions.get(userId);
    }

    public boolean isOnline(String userId) {
        WebSocketSession session = sessions.get(userId);

        return session != null && session.isOpen();
    }
}
