package com.jse.chat.messaging;

import com.jse.chat.domain.ChatMessage;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MessageProducerTest {

    @Test
    void shouldPutMessageIntoQueue() throws Exception {

        BlockingQueue<ChatMessage> queue = new LinkedBlockingQueue<>();

        ChatQueueProperties properties = mock(ChatQueueProperties.class);
        when(properties.getMaxAttempts()).thenReturn(1);
        when(properties.getTimeoutMs()).thenReturn(0L);

        MessageProducer producer = new MessageProducer(queue, properties);

        ChatMessage msg = new ChatMessage("john", "hello", Instant.now());

        producer.publish(msg);

        assertEquals(1, queue.size());
    }
}