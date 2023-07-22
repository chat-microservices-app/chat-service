package com.chatapp.chatservice.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Builder
public class UserDTO {

    @JsonProperty("userId")
    UUID userId;
    @JsonProperty("firstName")
    String firstName;

    @JsonProperty("lastName")
    String lastName;

    @JsonProperty("pictureUrl")
    String pictureUrl;

    @JsonProperty("username")
    String username;

}
