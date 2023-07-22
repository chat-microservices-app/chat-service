package com.chatapp.chatservice.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record RoomForm(

        @NotNull @NotBlank
        @Size(min = 4, max = 255, message = "Room name must be less than 255 characters and more than 4 characters")
        String name,
        @NotNull
        UUID creatorId,

        // TODO: create ms to handle image uploading to an s3 bucket
        // @NotNull @NotBlank
        //@Pattern(regexp = "data:image\\/([a-zA-Z]*);base64,([^\"]*)")
        String pictureUrl

) {
}
