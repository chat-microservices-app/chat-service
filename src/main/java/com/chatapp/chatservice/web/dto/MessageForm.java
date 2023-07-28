package com.chatapp.chatservice.web.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;

import java.time.OffsetDateTime;
import java.util.TimeZone;
import java.util.UUID;


public record MessageForm(

        UUID messageId,

        @NotEmpty(message = "message must not be null")
        String message,

        @Value("#{target.createdBy.userId}") @NotNull(message = "userId must not be null")
        UUID userId,

        @Value("#{target.room.roomId}") @NotNull(message = "roomId must not be null")
        UUID roomId,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        OffsetDateTime createdAt,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        OffsetDateTime updatedAt
) {
}
