package com.chatapp.chatservice.web.dto;

public record RoomUpdateForm(
        String name,

        String pictureUrl,

        MemberDTO requestedBy
) {
}
