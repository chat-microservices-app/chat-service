package com.chatapp.chatservice.config.kafka;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@EnableKafka
@Configuration
public class ProducerConfiguration {


    @Value("${spring.kafka.topic.chat-messaging}")
    private String chatMessageTopic;

    @Value("${spring.kafka.topic.chat-messaging-delete}")
    private String chatMessageDeleteTopic;

    @Value("${spring.kafka.topic.chat-messaging-update}")
    private String chatMessageUpdateTopic;

    @Bean
    public NewTopic sendMessageTopic() {
        return TopicBuilder.name(chatMessageTopic)
                .build();
    }

    @Bean
    public NewTopic deleteMessageTopic() {
        return TopicBuilder.name(chatMessageDeleteTopic)
                .build();
    }

    @Bean
    public NewTopic updateMessageTopic() {
        return TopicBuilder.name(chatMessageUpdateTopic)
                .build();
    }
}
