package com.chatapp.chatservice.service;

import com.chatapp.chatservice.domain.Role;

import java.util.List;

public interface RoleService {

    Role updateRole(String role, List<String> permissions);
}
