package com.chatapp.chatservice.web;

import com.chatapp.chatservice.config.rest.RestProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RequestMapping(RestProperties.ROOT + "/v1" + RestProperties.CHATS.ROOM.ROOT)
@RestController
public class RoomController {
    @GetMapping(path = "{roomId}", produces = "application/json")
    public ResponseEntity<?> getRoom(@PathVariable UUID roomId) {
        return ResponseEntity.ok().build();
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> createRoom(Object roomForm) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "{roomId}", produces = "application/json")
    public ResponseEntity<?> deleteRoom(@PathVariable UUID roomId) {
        return ResponseEntity.ok().build();
    }


    @PutMapping(path = "{roomId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> updateRoom(@PathVariable UUID roomId, Object roomForm) {
        return ResponseEntity.ok().build();
    }


    @PutMapping(path = "{roomId}" + RestProperties.CHATS.ROOM.JOIN, produces = "application/json")
    public ResponseEntity<?> joinRoom(@PathVariable UUID roomId) {
        return ResponseEntity.ok().build();
    }
}
