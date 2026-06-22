package com.jse.chat.messaging;

import com.jse.chat.domain.ChatMessage;
import com.jse.chat.websocket.MessageBroadcaster;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageConsumer {

    private final BlockingQueue<ChatMessage> queue;
    private final MessageBroadcaster broadcaster;

    private final AtomicBoolean running = new AtomicBoolean(true);
    private final ExecutorService executor =
            Executors.newSingleThreadExecutor(r -> {
                Thread t = new Thread(r);
                t.setName("chat-consumer-thread");
                t.setDaemon(true);
                return t;
            });

    @PostConstruct
    public void start() {
        log.info("Starting chat message consumer");

        executor.submit(this::consume);
    }

    @PreDestroy
    public void shutdown() {
        log.info("Shutting down chat message consumer");

        running.set(false);
        executor.shutdownNow();
    }

    private void consume() {

        while (running.get() && !Thread.currentThread().isInterrupted()) {
            try {
                ChatMessage msg = queue.take();

                log.info("Consumed message user={}", msg.getUsername());

                try {
                    broadcaster.broadcast(msg);
                    log.info("Broadcast success user={}", msg.getUsername());
                } catch (Exception ex) {
                    log.error("Broadcast failed user={}", msg.getUsername(), ex);
                }

            } catch (InterruptedException e) {
                log.warn("Consumer thread interrupted");
                Thread.currentThread().interrupt();
                break;

            } catch (Exception ex) {
                log.error("Unexpected consumer error", ex);
            }
        }

        log.info("Message consumer stopped");
    }
}