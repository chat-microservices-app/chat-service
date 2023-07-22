package com.chatapp.chatservice.web.dto;

import java.util.UUID;

public record RoomDTO(
        UUID roomId,
        String name,
        String pictureUrl

) {
}
