package com.chatapp.chatservice.kafka;


import com.chatapp.chatservice.config.rest.RestProperties;
import com.chatapp.chatservice.web.dto.MessageForm;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatMessagingConsumerImpl implements ChatMessagingConsumer {


    private final SimpMessagingTemplate simpMessagingTemplate;

    @KafkaListener(
            topics = "${spring.kafka.topic.chat-messaging}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    @Override
    public void consume(MessageForm message) {
        simpMessagingTemplate.convertAndSend("/topic" +
                RestProperties.ROOT + "/v1" +
                RestProperties.CHATS.ROOT +
                RestProperties.CHATS.ROOM.ROOT +
                message.roomId() +
                RestProperties.CHATS.MESSAGE.ROOT, message);
    }
}
