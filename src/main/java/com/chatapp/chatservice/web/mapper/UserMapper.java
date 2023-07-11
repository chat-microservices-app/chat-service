package com.chatapp.chatservice.web.mapper;


import com.chatapp.chatservice.domain.User;
import com.chatapp.chatservice.web.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    void updateUser(UserDTO updatedUser, @MappingTarget User userObjectToUpdate);

}
