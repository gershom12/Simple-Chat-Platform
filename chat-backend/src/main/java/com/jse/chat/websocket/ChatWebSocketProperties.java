package com.jse.chat.websocket;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "chat.websocket")
public class ChatWebSocketProperties {

    private String destination = "/topic/chat";
    private String endpoint;
    private String appPrefix;
    private List<String> brokerPrefixes;
}