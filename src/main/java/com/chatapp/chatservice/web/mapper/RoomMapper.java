package com.chatapp.chatservice.web.mapper;


import com.chatapp.chatservice.domain.Room;
import com.chatapp.chatservice.web.dto.RoomForm;
import com.chatapp.chatservice.web.dto.RoomUpdateForm;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = org.mapstruct.NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
public interface RoomMapper {


    Room roomFormToRoom(RoomForm room);

    void updateRoom(RoomUpdateForm updatedRoom, @MappingTarget Room roomObjectToUpdate);
}
