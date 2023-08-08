package com.chatapp.chatservice.config.websocket;


import com.chatapp.chatservice.config.redis.domain.ActiveWebSocketUser;
import jakarta.validation.constraints.NotNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import java.security.Principal;
import java.util.Calendar;


@Log4j2
public class WebSocketConnectHandler<S> implements ApplicationListener<SessionConnectedEvent> {

    private final RedisTemplate<String, ActiveWebSocketUser> redisTemplate;

    public WebSocketConnectHandler(RedisTemplate<String, ActiveWebSocketUser> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onApplicationEvent(@NotNull SessionConnectedEvent event) {
        MessageHeaders headers = event.getMessage().getHeaders();

        // get the username from the headers
        Principal principal = SimpMessageHeaderAccessor.getUser(headers);
        if (principal == null) {
            return;
        }
        // get the session id from the headers
        String sessionId = SimpMessageHeaderAccessor.getSessionId(headers);
        assert sessionId != null;
        log.debug("Connecting websocket with session id: {} and with session {}", sessionId, principal);
        redisTemplate.opsForValue().set(sessionId, ActiveWebSocketUser
                .builder()
                .id(sessionId)
                .connectTime(Calendar.getInstance())
                .username(principal.getName())
                .build()
        );

    }
}
