package com.chatapp.chatservice.web.controller;

import com.chatapp.chatservice.annotation.CreateMessagePerm;
import com.chatapp.chatservice.config.api.rest.RestProperties;
import com.chatapp.chatservice.config.api.websocket.WebSocketProperties;
import com.chatapp.chatservice.kafka.ChatMessagingProducer;
import com.chatapp.chatservice.service.MessageService;
import com.chatapp.chatservice.web.dto.MessageDTO;
import com.chatapp.chatservice.web.dto.MessageForm;
import com.chatapp.chatservice.web.paging.ObjectPagedList;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
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


    @MessageMapping("/send/{roomId}")
    @SendTo(WebSocketProperties.ROOT_CHANNEL + WebSocketProperties.Rooms.ROOT +
            ".{roomId}." + WebSocketProperties.Rooms.Messages.ROOT +
            WebSocketProperties.Rooms.Messages.SEND)
    public MessageForm broadcastMessage(@DestinationVariable UUID roomId, @Validated @Payload MessageForm messageForm) {
        return messageForm;
    }

    @MessageMapping("/update/{roomId}")
    @SendTo(WebSocketProperties.ROOT_CHANNEL + WebSocketProperties.Rooms.ROOT +
            ".{roomId}." + WebSocketProperties.Rooms.Messages.ROOT +
            WebSocketProperties.Rooms.Messages.UPDATE)
    public MessageForm broadcastUpdateMessage(@DestinationVariable UUID roomId, @Validated @Payload MessageForm messageForm) {
        return messageForm;
    }

    @MessageMapping("/delete/{roomId}")
    @SendTo(WebSocketProperties.ROOT_CHANNEL + WebSocketProperties.Rooms.ROOT +
            ".{roomId}." + WebSocketProperties.Rooms.Messages.ROOT +
            WebSocketProperties.Rooms.Messages.DELETE)
    public MessageForm broadcastDeleteMessage(@DestinationVariable UUID roomId, @Validated @Payload MessageForm messageForm) {
        return messageForm;
    }

    @CreateMessagePerm
    @PostMapping(produces = "application/json", consumes = "application/json")
    public void createMessage(@PathVariable UUID roomId, @Validated @RequestBody MessageForm messageForm) {
        chatMessagingProducer.sendMessage(messageForm);
    }

    @DeleteMapping(path = "/{messageId}", produces = "application/json")
    public void deleteMessage(@PathVariable UUID roomId, @PathVariable UUID messageId, @RequestParam(value = "userId") UUID userId) {
        log.info("messageId: " + messageId + " userId: " + userId + " roomId: " + roomId);
        MessageForm messageForm = new MessageForm(messageId, "temp", userId, roomId, null, null);
        chatMessagingProducer.deleteMessage(messageForm);
    }

    @PutMapping(path = "{messageId}", produces = "application/json", consumes = "application/json")
    public void updateMessage(@PathVariable UUID roomId, @PathVariable UUID messageId, @RequestBody @Validated MessageForm messageForm) {
        chatMessagingProducer.updateMessage(messageForm);

    }
}
