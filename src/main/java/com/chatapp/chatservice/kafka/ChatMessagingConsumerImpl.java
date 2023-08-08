package com.chatapp.chatservice.kafka;


import com.chatapp.chatservice.config.api.websocket.WebSocketProperties;
import com.chatapp.chatservice.service.MessageService;
import com.chatapp.chatservice.web.dto.MessageForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Log4j2
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
        simpMessagingTemplate.convertAndSend(WebSocketProperties.ROOT_CHANNEL + WebSocketProperties.Rooms.ROOT +
                        "." + message.roomId() + "." + WebSocketProperties.Rooms.Messages.ROOT +
                        WebSocketProperties.Rooms.Messages.SEND,
                messageService.saveMessage(message));
    }


    @KafkaListener(
            topics = "${spring.kafka.topic.chat-messaging-update}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    @Override
    public void consumeUpdateMessage(MessageForm message) {

        simpMessagingTemplate
                .convertAndSend(WebSocketProperties.ROOT_CHANNEL + WebSocketProperties.Rooms.ROOT +
                                "." + message.roomId() + "." + WebSocketProperties.Rooms.Messages.ROOT +
                                WebSocketProperties.Rooms.Messages.UPDATE,
                        messageService.updateMessage(message));
    }


    @KafkaListener(
            topics = "${spring.kafka.topic.chat-messaging-delete}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    @Override
    public void consumeDeleteMessage(MessageForm message) {
        simpMessagingTemplate
                .convertAndSend(WebSocketProperties.ROOT_CHANNEL + WebSocketProperties.Rooms.ROOT +
                                "." + message.roomId() + "." + WebSocketProperties.Rooms.Messages.ROOT +
                                WebSocketProperties.Rooms.Messages.DELETE,
                        messageService.deleteMessage(message.messageId(), message.userId()));
    }
}
