package com.chatapp.chatservice.web.mapper;


import com.chatapp.chatservice.domain.Authority;
import com.chatapp.chatservice.domain.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {


    @Mapping(target ="roleName", source = "role")
    Role createRole(String role);


    Authority createAuthority(String permission);
}
