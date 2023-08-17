package com.chatapp.chatservice.config.websocket;


import com.chatapp.chatservice.config.redis.domain.ActiveWebSocketUser;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;



@Log4j2
public class WebSocketDisconnectHandler<S> implements ApplicationListener<SessionDisconnectEvent> {

    private final RedisTemplate<String, ActiveWebSocketUser> redisTemplate;



    public WebSocketDisconnectHandler(RedisTemplate<String, ActiveWebSocketUser> redisTemplate) {
        super();
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        log.debug("Disconnecting websocket with session id: {}", sessionId);
        redisTemplate.delete(sessionId);
    }
}
