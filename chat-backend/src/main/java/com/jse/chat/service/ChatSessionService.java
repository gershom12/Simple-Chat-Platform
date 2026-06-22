package com.jse.chat.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ChatSessionService {

    private static final Logger log = LoggerFactory.getLogger(ChatSessionService.class);

    private final Map<String, Instant> activeUsers = new ConcurrentHashMap<>();

    public void join(String username) {

        validate(username);

        activeUsers.put(username, Instant.now());

        log.info("User JOINED session: {} | activeUsers={}", username, activeUsers.size());
    }

    public void leave(String username) {

        if (username == null) return;

        activeUsers.remove(username);

        log.info("User LEFT session: {} | activeUsers={}", username, activeUsers.size());
    }

    public boolean isActive(String username) {
        return activeUsers.containsKey(username);
    }

    public Set<String> getActiveUsers() {
        return activeUsers.keySet();
    }

    private void validate(String username) {
        if (username == null || username.isBlank()) {
            log.warn("Invalid join attempt");
            throw new IllegalArgumentException("username cannot be null or blank");
        }
    }
}