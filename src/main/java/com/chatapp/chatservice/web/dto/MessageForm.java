package com.chatapp.chatservice.web.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record MessageForm(

        @NotNull
        String message,

        @NotNull
        UUID userId,

        @NotNull
        UUID roomId
) {
}
