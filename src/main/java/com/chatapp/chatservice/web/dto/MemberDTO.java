package com.chatapp.chatservice.web.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record MemberDTO(

        UUID memberId,

        UUID roomId,

        @NotNull
        String username,

        String pictureUrl
) {
}
