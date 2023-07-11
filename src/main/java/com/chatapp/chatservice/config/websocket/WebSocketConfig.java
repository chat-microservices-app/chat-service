package com.chatapp.chatservice.config.websocket;


import com.chatapp.chatservice.config.rest.RestProperties;
import com.chatapp.chatservice.config.security.SecurityManager;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.apache.kafka.common.security.oauthbearer.internals.unsecured.OAuthBearerIllegalTokenException;
import org.apache.kafka.common.security.oauthbearer.internals.unsecured.OAuthBearerValidationResult;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;
import java.util.NoSuchElementException;

@Configuration
@Log4j2
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final SecurityManager securityManager;

    final String BASE_URL = RestProperties.ROOT + "/v1" + RestProperties.CHATS.ROOT;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // to handle gateway websocket requests and forward them to the chat service
        registry.addEndpoint(BASE_URL + "/ws-chatapp")
                .setAllowedOriginPatterns("*")
                .withSockJS()
                .setWebSocketEnabled(true);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/channel");
        registry.setApplicationDestinationPrefixes("/app");
    }


    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(@Nonnull Message<?> message, @Nonnull MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                log.info("check  inside presend {}", accessor);
                assert accessor != null;
                log.info("StompCommand: {}", accessor.getCommand());
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    List<String> auth =  accessor.getNativeHeader(HttpHeaders.AUTHORIZATION);
                    if (auth != null && auth.size() > 0) {
                        log.debug("Auth header: {}", auth);
                        // gets the jwt token from the header
                        String tokenData = StringUtils.substringAfter(auth.get(0), RestProperties.TOKEN_PREFIX);
                        Authentication user = securityManager
                                .authenticate(tokenData)
                                .orElseThrow(() -> new NoSuchElementException("Token not found"));
                        accessor.setUser(user);
                        accessor.setLeaveMutable(true);
                        return MessageBuilder.createMessage(message.getPayload(), accessor.getMessageHeaders());
                    }
                    // TODO add correct http status code
                    throw new OAuthBearerIllegalTokenException(OAuthBearerValidationResult.newFailure("Bearer token is missing"));
                }
                return message;
            }


        });
    }
}
