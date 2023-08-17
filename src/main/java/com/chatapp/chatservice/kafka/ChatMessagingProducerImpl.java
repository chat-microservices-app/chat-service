package com.chatapp.chatservice.kafka;

import com.chatapp.chatservice.web.dto.MessageForm;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ChatMessagingProducerImpl implements ChatMessagingProducer {

    private final KafkaTemplate<String, MessageForm> kafkaTemplate;


    private final NewTopic sendMessageTopic;

    private final NewTopic deleteMessageTopic;

    private final NewTopic updateMessageTopic;


    public ChatMessagingProducerImpl(KafkaTemplate<String, MessageForm> kafkaTemplate,
                                     @Qualifier("sendMessageTopic") NewTopic sendMessageTopic,
                                     @Qualifier("deleteMessageTopic") NewTopic deleteMessageTopic,
                                     @Qualifier("updateMessageTopic") NewTopic updateMessageTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.sendMessageTopic = sendMessageTopic;
        this.deleteMessageTopic = deleteMessageTopic;
        this.updateMessageTopic = updateMessageTopic;
    }

    @Override
    public void sendMessage(MessageForm messageForm) {
        Message<MessageForm> message = MessageBuilder
                .withPayload(messageForm)
                .setHeader(KafkaHeaders.TOPIC, sendMessageTopic.name())
                .build();
        kafkaTemplate.send(message);
    }

    @Override
    public void deleteMessage(MessageForm messageToDelete) {
        Message<MessageForm> messageToRemove = MessageBuilder
                .withPayload(messageToDelete)
                .setHeader(KafkaHeaders.TOPIC, deleteMessageTopic.name())
                .build();
        kafkaTemplate.send(messageToRemove);
    }

    @Override
    public void updateMessage(MessageForm messageForm) {
        Message<MessageForm> message = MessageBuilder
                .withPayload(messageForm)
                .setHeader(KafkaHeaders.TOPIC, updateMessageTopic.name())
                .build();
        kafkaTemplate.send(message);
    }
}
