package com.chatapp.chatservice.config.redis;

import com.chatapp.chatservice.config.redis.domain.ActiveWebSocketUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, ActiveWebSocketUser> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, ActiveWebSocketUser> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // Use Jackson2JsonRedisSerializer to serialize/deserialize ActiveWebSocketUser objects to/from JSON
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();

        template.setDefaultSerializer(serializer);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.setHashValueSerializer(serializer);
        template.afterPropertiesSet();
        return template;
    }
}
