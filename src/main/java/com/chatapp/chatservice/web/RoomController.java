package com.chatapp.chatservice.web;

import com.chatapp.chatservice.config.rest.RestProperties;
import com.chatapp.chatservice.domain.Room;
import com.chatapp.chatservice.service.RoomService;
import com.chatapp.chatservice.web.dto.RoomDTO;
import com.chatapp.chatservice.web.dto.RoomForm;
import com.chatapp.chatservice.web.dto.RoomUpdateForm;
import com.chatapp.chatservice.web.paging.ObjectPagedList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;


@RequestMapping(RestProperties.ROOT + "/v1" + RestProperties.CHATS.ROOM.ROOT)
@RequiredArgsConstructor
@RestController
public class RoomController {

    private final RoomService roomService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<ObjectPagedList<RoomDTO>> getRoomList(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                @RequestParam(name = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(roomService.getRoomList(PageRequest.of(page, size)));

    }

    @GetMapping(path = "{roomId}", produces = "application/json")
    public ResponseEntity<Room> getRoom(@PathVariable UUID roomId) {
        return ResponseEntity.ok(roomService.getRoomInformation(roomId));
    }


    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<UUID> createRoom(@RequestBody @Validated RoomForm roomForm) {
        URI location = URI.create(RestProperties.ROOT + "/v1" + RestProperties.CHATS.ROOM.ROOT + "/" + roomService.createRoom(roomForm).toString());
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(path = "{roomId}", produces = "application/json")
    public ResponseEntity<?> deleteRoom(@PathVariable UUID roomId, @RequestParam(name = "userId") UUID memberId) {
        return ResponseEntity.accepted().body(roomService.deleteRoom(roomId, memberId));
    }

    @PutMapping(path = "{roomId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> updateRoom(@PathVariable UUID roomId, @RequestBody RoomUpdateForm roomForm) {
        URI location = URI.create(URI.create(RestProperties.ROOT + "/v1" + RestProperties.CHATS.ROOM.ROOT + "/" +
                roomService.updateRoom(roomId, roomForm).toString()).toString());
        return ResponseEntity.noContent().location(location).build();
    }

    @PutMapping(path = "{roomId}" + RestProperties.CHATS.ROOM.JOIN_REQUEST, produces = "application/json")
    public ResponseEntity<?> requestToJoinRoom(@PathVariable UUID roomId, @RequestParam UUID userId) {
        return ResponseEntity.accepted().body(roomService.handleJoinRoom(roomId, userId));
    }


}
