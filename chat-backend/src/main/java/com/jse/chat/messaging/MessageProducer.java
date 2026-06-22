package com.jse.chat.messaging;

import com.jse.chat.domain.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageProducer {

    private final BlockingQueue<ChatMessage> queue;
    private final ChatQueueProperties properties;

    public void publish(ChatMessage message) {

        log.info("Publishing message user={}", message.getUsername());

        int maxAttempts = properties.getMaxAttempts();
        long timeoutMs = properties.getTimeoutMs();

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {

            try {
                boolean accepted = queue.offer(
                        message,
                        timeoutMs,
                        TimeUnit.MILLISECONDS
                );

                if (accepted) {
                    log.info("Message queued successfully attempt={}", attempt);
                    return;
                }

                log.warn("Queue backpressure detected attempt={}", attempt);

            } catch (InterruptedException e) {

                log.error("Interrupted while publishing message user={}", message.getUsername(), e);
                Thread.currentThread().interrupt();
                return;

            } catch (Exception ex) {

                log.warn("Queue publish failure attempt={}", attempt, ex);
            }
        }

        log.error(
                "Failed to publish message after {} attempts user={}",
                maxAttempts,
                message.getUsername()
        );

        throw new IllegalStateException("Failed to publish message to queue");
    }
}