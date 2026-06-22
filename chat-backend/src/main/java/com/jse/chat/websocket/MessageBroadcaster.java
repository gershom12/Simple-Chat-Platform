package com.jse.chat.websocket;

import com.jse.chat.domain.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageBroadcaster {

    private final SimpMessagingTemplate template;
    private final ChatWebSocketProperties properties;

    public void broadcast(ChatMessage message) {

        String destination = properties.getDestination();

        log.info(
                "Broadcasting message user={} destination={}",
                message.getUsername(),
                destination
        );

        template.convertAndSend(destination, message);

        log.info(
                "Broadcast completed user={}",
                message.getUsername()
        );
    }
}