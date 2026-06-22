package com.jse.chat.messaging;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "chat.queue")
public class ChatQueueProperties {

    private int maxAttempts = 3;
    private long timeoutMs = 500;
    private int capacity = 1000;
}