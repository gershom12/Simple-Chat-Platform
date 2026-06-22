package com.jse.chat.controller;

import com.jse.chat.dto.JoinRequest;
import com.jse.chat.dto.SendMessageRequest;
import com.jse.chat.service.ChatService;
import com.jse.chat.service.ChatSessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatService chatService;
    private final ChatSessionService chatSessionService;

    @PostMapping("/send")
    public ResponseEntity<Void> send(
            @Valid @RequestBody final SendMessageRequest request) {

        log.info(
                "Received chat message from user [{}]",
                request.username()
        );

        chatService.sendMessage(request);

        return ResponseEntity.accepted().build();
    }

    @PostMapping("/join")
    public ResponseEntity<Void> join(
            @Valid @RequestBody final JoinRequest request) {

        log.info(
                "User [{}] requested to join chat",
                request.username()
        );

        chatSessionService.join(request.username());

        return ResponseEntity.ok().build();
    }
}