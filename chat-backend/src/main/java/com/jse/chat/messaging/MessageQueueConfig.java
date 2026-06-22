package com.jse.chat.messaging;

import com.jse.chat.domain.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
@RequiredArgsConstructor
public class MessageQueueConfig {

    private final ChatQueueProperties properties;

    @Bean
    public BlockingQueue<ChatMessage> messageQueue() {

        int capacity = properties.getCapacity();

        return new LinkedBlockingQueue<>(capacity);
    }
}