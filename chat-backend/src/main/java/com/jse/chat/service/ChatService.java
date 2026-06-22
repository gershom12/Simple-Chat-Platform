package com.jse.chat.service;

import com.jse.chat.domain.ChatMessage;
import com.jse.chat.dto.SendMessageRequest;
import com.jse.chat.messaging.MessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    private final MessageProducer producer;
    private final Clock clock;

    public void sendMessage(SendMessageRequest request) {

        ChatMessage message = toDomain(request);

        log.info(
                "Sending message user={} contentLength={}",
                message.getUsername(),
                message.getContent().length()
        );

        producer.publish(message);
    }

    private ChatMessage toDomain(SendMessageRequest request) {
        return new ChatMessage(
                request.username(),
                request.content(),
                Instant.now(clock)
        );
    }
}