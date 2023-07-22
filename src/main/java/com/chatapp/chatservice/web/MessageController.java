package com.chatapp.chatservice.web;

import com.chatapp.chatservice.annotation.CreateMessagePerm;
import com.chatapp.chatservice.config.rest.RestProperties;
import com.chatapp.chatservice.kafka.ChatMessagingProducer;
import com.chatapp.chatservice.service.MessageService;
import com.chatapp.chatservice.web.dto.MessageDTO;
import com.chatapp.chatservice.web.dto.MessageForm;
import com.chatapp.chatservice.web.paging.ObjectPagedList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class MessageController {

    private final ChatMessagingProducer chatMessagingProducer;
    private final MessageService messageService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<ObjectPagedList<MessageDTO>> getMessagesForRoom(@PathVariable UUID roomId,
                                                                          @RequestParam(name = "page", defaultValue = "0") int page,
                                                                          @RequestParam(name = "size", defaultValue = "10") int size,
                                                                          @RequestParam(name = "sort", defaultValue = "createdAt") String sort) {
        return ResponseEntity.ok(
                messageService
                        .getMessagesByRoomId(roomId,
                                PageRequest.of(page, size,
                                        Sort.by(sort)
                                                .descending())
                        )
        );

    }

    // get messages by a user for a room
    @GetMapping(path = "/{userId}", produces = "application/json")
    public ResponseEntity<?> getMessagesForRoomByUser(@PathVariable UUID roomId, @PathVariable UUID userId) {
        return ResponseEntity.ok().build();
    }


    @MessageMapping("/send/{roomId}")
    @SendTo("/channel" + RestProperties.CHATS.ROOM.ROOT +
            "/{roomId}" + RestProperties.CHATS.MESSAGE.ROOT)
    public MessageForm broadcastMessage(@DestinationVariable UUID roomId, @Validated @Payload MessageForm messageForm) {
        return messageForm;
    }

    @CreateMessagePerm
    @PostMapping(produces = "application/json", consumes = "application/json")
    public void createMessage(@PathVariable UUID roomId, @Validated @RequestBody MessageForm messageForm) {
        chatMessagingProducer.sendMessage(messageForm);
    }

    @DeleteMapping(path = "{messageId}", produces = "application/json")
    public ResponseEntity<?> deleteMessage(@PathVariable UUID roomId, @PathVariable UUID messageId) {
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "{messageId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> updateMessage(@PathVariable UUID roomId, @PathVariable UUID messageId, MessageForm messageForm) {
        return ResponseEntity.ok().build();
    }
}
