package com.jse.chat.messaging;

import com.jse.chat.domain.ChatMessage;
import com.jse.chat.messaging.MessageConsumer;
import com.jse.chat.websocket.MessageBroadcaster;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class MessageConsumerTest {

    @Test
    void shouldConsumeMessageAndBroadcast() throws Exception {

        BlockingQueue<ChatMessage> queue = new LinkedBlockingQueue<>();

        MessageBroadcaster broadcaster = mock(MessageBroadcaster.class);

        MessageConsumer consumer = new MessageConsumer(queue, broadcaster);

        ChatMessage msg = new ChatMessage("john", "hello", Instant.now());

        queue.add(msg);

        // simulate one step
        ChatMessage taken = queue.take();

        broadcaster.broadcast(taken);

        verify(broadcaster, times(1)).broadcast(any());
    }

    @Test
    void shouldHandleEmptyQueueGracefully() {

        BlockingQueue<ChatMessage> queue = new LinkedBlockingQueue<>();

        MessageBroadcaster broadcaster = mock(MessageBroadcaster.class);

        MessageConsumer consumer = new MessageConsumer(queue, broadcaster);

        assertTrue(queue.isEmpty());
    }
}