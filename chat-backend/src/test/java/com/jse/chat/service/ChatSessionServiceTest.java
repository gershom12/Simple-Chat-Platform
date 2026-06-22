package com.jse.chat.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChatSessionServiceTest {

    private ChatSessionService service;

    @BeforeEach
    void setUp() {
        service = new ChatSessionService();
    }

    @Test
    void shouldJoinUser() {

        service.join("john");

        assertTrue(service.isActive("john"));
    }

    @Test
    void shouldRemoveUser() {

        service.join("john");
        service.leave("john");

        assertFalse(service.isActive("john"));
    }

    @Test
    void shouldRejectInvalidUsername() {

        assertThrows(IllegalArgumentException.class,
                () -> service.join(""));
    }
}