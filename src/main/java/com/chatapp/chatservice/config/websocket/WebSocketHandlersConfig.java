package com.chatapp.chatservice.config.websocket;


import com.chatapp.chatservice.config.redis.domain.ActiveWebSocketUser;
import org.hibernate.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class WebSocketHandlersConfig<S extends Session> {

    @Bean
    public WebSocketConnectHandler<S> webSocketConnectHandler(
            RedisTemplate<String, ActiveWebSocketUser> redisTemplate) {
        return new WebSocketConnectHandler<>(redisTemplate);
    }

    @Bean
    public WebSocketDisconnectHandler<S> webSocketDisconnectHandler(
            RedisTemplate<String, ActiveWebSocketUser> redisTemplate) {
        return new WebSocketDisconnectHandler<>(redisTemplate);
    }
}
