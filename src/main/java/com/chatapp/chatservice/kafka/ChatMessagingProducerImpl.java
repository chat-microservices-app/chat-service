package com.chatapp.chatservice.kafka;

import com.chatapp.chatservice.web.dto.MessageForm;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessagingProducerImpl implements ChatMessagingProducer {

    private final KafkaTemplate<String, MessageForm> kafkaTemplate;

    private final NewTopic topic;

    @Override
    public void sendMessage(MessageForm messageForm) {
        Message<MessageForm> message = MessageBuilder
                .withPayload(messageForm)
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .build();
        kafkaTemplate.send(message);
    }
}
