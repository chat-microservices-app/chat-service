package com.chatapp.chatservice.config.redis.domain;


import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Calendar;

@Data
@Builder
public class ActiveWebSocketUser implements Serializable {

    private String id;
    private String username;
    private Calendar connectTime;

}
