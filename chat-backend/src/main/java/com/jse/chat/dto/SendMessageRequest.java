package com.jse.chat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SendMessageRequest(

        @NotBlank(message = "Username is required")
        @Size(max = 50, message = "Username cannot exceed 50 characters")
        String username,

        @NotBlank(message = "Message content is required")
        @Size(max = 1000, message = "Message content cannot exceed 1000 characters")
        String content

) {
}