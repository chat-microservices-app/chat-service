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

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(chatMessageTopic)
                .build();
    }
}
