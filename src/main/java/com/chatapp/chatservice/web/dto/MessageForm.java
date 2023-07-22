package com.chatapp.chatservice.web.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;

import java.time.OffsetDateTime;
import java.util.TimeZone;
import java.util.UUID;


public record MessageForm(

        UUID messageId,

        @NotNull
        String message,

        @NotNull @Value("#{target.createdBy.userId}")
        UUID userId,

        @NotNull @Value("#{target.room.roomId}")
        UUID roomId,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        OffsetDateTime createdAt,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        OffsetDateTime updatedAt
) {
}
