package com.jse.chat.websocket;

import com.jse.chat.domain.ChatMessage;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class MessageBroadcasterTest {

    @Test
    void shouldBroadcastMessageSuccessfully() {

        SimpMessagingTemplate template = mock(SimpMessagingTemplate.class);

        ChatWebSocketProperties properties = mock(ChatWebSocketProperties.class);
        when(properties.getDestination()).thenReturn("/topic/chat");

        MessageBroadcaster broadcaster =
                new MessageBroadcaster(template, properties);

        ChatMessage msg = new ChatMessage("john", "hello", Instant.now());

        broadcaster.broadcast(msg);

        verify(template, times(1))
                .convertAndSend(eq("/topic/chat"), eq(msg));
    }

    @Test
    void shouldCallTemplateEvenWhenBroadcastRuns() {

        SimpMessagingTemplate template = mock(SimpMessagingTemplate.class);

        ChatWebSocketProperties properties = mock(ChatWebSocketProperties.class);
        when(properties.getDestination()).thenReturn("/topic/chat");

        MessageBroadcaster broadcaster =
                new MessageBroadcaster(template, properties);

        ChatMessage msg = new ChatMessage("john", "hello", Instant.now());

        broadcaster.broadcast(msg);

        verify(template, times(1))
                .convertAndSend(anyString(), any(ChatMessage.class));
    }
}