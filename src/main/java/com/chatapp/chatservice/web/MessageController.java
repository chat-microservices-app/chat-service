package com.chatapp.chatservice.web;

import com.chatapp.chatservice.config.rest.RestProperties;
import com.chatapp.chatservice.kafka.ChatMessagingProducer;
import com.chatapp.chatservice.web.dto.MessageForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RequestMapping(RestProperties.ROOT + "/v1" +
        RestProperties.CHATS.ROOM.ROOT +
        "/{roomId}" +
        RestProperties.CHATS.MESSAGE.ROOT
)
@MessageMapping(
        RestProperties.ROOT + "/v1" +
                RestProperties.CHATS.ROOM.ROOT +
                "/{roomId}" +
                RestProperties.CHATS.MESSAGE.ROOT
)
@RestController
@RequiredArgsConstructor
public class MessageController {

    private final ChatMessagingProducer chatMessagingProducer;

    @GetMapping(produces = "application/json")
    public ResponseEntity<?> getMessagesForRoom(@PathVariable UUID roomId) {
        return ResponseEntity.ok().build();
    }

    // get messages by a user for a room

    @GetMapping(path = "{userId}", produces = "application/json")
    public ResponseEntity<?> getMessagesForRoomByUser(@PathVariable UUID roomId, @PathVariable UUID userId) {
        return ResponseEntity.ok().build();
    }

    @MessageMapping("/send")
    @SendTo("/topic" + RestProperties.ROOT + "/v1" +
            RestProperties.CHATS.ROOT +
            RestProperties.CHATS.ROOM.ROOT +
            "/{roomId}" +
            RestProperties.CHATS.MESSAGE.ROOT)
    public MessageForm broadcastMessage(@DestinationVariable UUID roomId, @Validated @Payload MessageForm messageForm) {
        return messageForm;
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public void createMessage(@PathVariable UUID roomId, @Validated @RequestBody MessageForm messageForm) {
        chatMessagingProducer.sendMessage(messageForm);
    }

    @DeleteMapping(path = "{messageId}", produces = "application/json")
    public ResponseEntity<?> deleteMessage(@PathVariable UUID roomId, @PathVariable UUID messageId) {
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "{messageId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> updateMessage(@PathVariable UUID roomId, @PathVariable UUID messageId, Object messageForm) {
        return ResponseEntity.ok().build();
    }
}
