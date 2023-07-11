package com.chatapp.chatservice.utils;

import com.chatapp.chatservice.enums.Role;
import org.antlr.v4.runtime.misc.Pair;

import java.util.List;

public class RoleHandler {

    public static Pair<String, List<String>> getRoleAndPermission(Role role) {
        List<String> permissions = Role.Permission.getPermissionsByRole(role).stream().toList();
        return new Pair<>(role.getLabel(), permissions);
    }
}
