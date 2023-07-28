package com.chatapp.chatservice.web.mapper;


import com.chatapp.chatservice.domain.Room;
import com.chatapp.chatservice.web.dto.RoomDTO;
import com.chatapp.chatservice.web.dto.RoomForm;
import com.chatapp.chatservice.web.dto.RoomUpdateForm;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = org.mapstruct.NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
public interface RoomMapper {


    Room roomFormToRoom(RoomForm room);


    RoomDTO toRoomDTO(Room room);

    List<RoomDTO> toRoomDTOs(List<Room> rooms);

    void updateRoom(RoomUpdateForm updatedRoom, @MappingTarget Room roomObjectToUpdate);
}
