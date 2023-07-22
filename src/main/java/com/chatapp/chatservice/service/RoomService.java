package com.chatapp.chatservice.service;

import com.chatapp.chatservice.domain.Room;
import com.chatapp.chatservice.web.dto.RoomDTO;
import com.chatapp.chatservice.web.dto.RoomForm;
import com.chatapp.chatservice.web.dto.RoomUpdateForm;
import com.chatapp.chatservice.web.paging.ObjectPagedList;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface RoomService {


    ObjectPagedList<RoomDTO> getRoomList(PageRequest page);


    ObjectPagedList<RoomDTO> getRoomsJoinedByUser(UUID userId, PageRequest page);


    ObjectPagedList<RoomDTO> getRoomsNotJoinedByUser(UUID userId, PageRequest page);

    Room getRoomInformation(UUID roomId);

    UUID createRoom(RoomForm roomForm);

    UUID deleteRoom(UUID roomId, UUID memberId);


    UUID updateRoom(UUID roomId, RoomUpdateForm roomForm);

    void requestJoinRoom(UUID roomId);

    UUID handleJoinRoom(UUID roomId, UUID userId);

}
