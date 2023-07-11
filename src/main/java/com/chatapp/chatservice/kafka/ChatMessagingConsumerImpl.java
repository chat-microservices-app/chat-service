package com.chatapp.chatservice.kafka;


import com.chatapp.chatservice.config.rest.RestProperties;
import com.chatapp.chatservice.service.MessageService;
import com.chatapp.chatservice.web.dto.MessageForm;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatMessagingConsumerImpl implements ChatMessagingConsumer {


    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageService messageService;

    @KafkaListener(
            topics = "${spring.kafka.topic.chat-messaging}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    @Override
    public void consume(MessageForm message) {
        // returns the saved message in db
        simpMessagingTemplate.convertAndSend("/channel" +
                RestProperties.CHATS.ROOM.ROOT +
                "/" + message.roomId() +
                RestProperties.CHATS.MESSAGE.ROOT,  messageService.saveMessage(message));
    }
}
