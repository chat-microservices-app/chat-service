package com.chatapp.chatservice.web.dto;

import java.util.UUID;

public record MemberDTO(
        UUID memberId,

        UUID roomId,

        String username
) {
}
