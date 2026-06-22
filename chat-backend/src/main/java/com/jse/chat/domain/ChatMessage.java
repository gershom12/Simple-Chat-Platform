package com.jse.chat.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Getter
@RequiredArgsConstructor
public class ChatMessage {

    private final String username;
    private final String content;
    private final Instant timestamp;
}