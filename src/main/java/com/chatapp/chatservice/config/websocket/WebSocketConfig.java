package com.chatapp.chatservice.config.websocket;


import com.chatapp.chatservice.config.api.rest.RestProperties;
import com.chatapp.chatservice.config.security.SecurityManager;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.apache.kafka.common.security.oauthbearer.internals.unsecured.OAuthBearerIllegalTokenException;
import org.apache.kafka.common.security.oauthbearer.internals.unsecured.OAuthBearerValidationResult;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.Authentication;
import org.springframework.session.Session;
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * This class is responsible for configuring the websocket connection
 * and the message broker
 * AbstractSessionWebSocketMessageBrokerConfigurer is used to enable storing the websocket in redis
 * for horizontal scaling
 */
@Configuration
@Log4j2
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
// ensure that this config is loaded before the security config to intercept the requests
@EnableScheduling
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractSessionWebSocketMessageBrokerConfigurer<Session> {


    @Value("${chat.relay.host}")
    private String relayHost;

    @Value("${chat.relay.port}")
    private String relayPort;

    @Value("${chat.relay.client.login}")
    private String clientLogin;

    @Value("${chat.relay.client.passcode}")
    private String clientPasscode;

    @Value("${chat.relay.system.login}")
    private String systemLogin;

    @Value("${chat.relay.system.passcode}")
    private String systemPasscode;

    private final SecurityManager securityManager;

    final String BASE_URL = RestProperties.ROOT + "/v1" + RestProperties.CHATS.ROOT;

    @Override
    protected void configureStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        // to handle gateway websocket requests and forward them to the chat service
        stompEndpointRegistry.addEndpoint(BASE_URL + "/ws-chatapp")
                .setAllowedOriginPatterns("*")
                .withSockJS()
                .setWebSocketEnabled(true);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableStompBrokerRelay("/topic/")
                .setRelayPort(Integer.parseInt(relayPort))
                .setRelayHost(relayHost)
                .setUserDestinationBroadcast("/topic/unresolved.user.dest")
                .setUserRegistryBroadcast("/topic/registry.broadcast")
                .setClientPasscode(clientPasscode)
                .setClientLogin(clientLogin)
                .setSystemLogin(systemLogin)
                .setSystemPasscode(systemPasscode);
        registry.setApplicationDestinationPrefixes("/app");
    }


    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(@Nonnull Message<?> message, @Nonnull MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                assert accessor != null;
                log.debug("StompCommand: {}", accessor.getCommand());

                //  On websocket connect event, authenticate the user and set the user in the message header
                // for authorization
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    List<String> auth = accessor.getNativeHeader(HttpHeaders.AUTHORIZATION);
                    if (auth != null && auth.size() > 0) {
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
