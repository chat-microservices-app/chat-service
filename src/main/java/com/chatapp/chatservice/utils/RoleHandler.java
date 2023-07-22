package com.chatapp.chatservice.utils;

import com.chatapp.chatservice.enums.DefaultRole;
import org.antlr.v4.runtime.misc.Pair;

import java.util.List;

public class RoleHandler {

    public static Pair<String, List<String>> getRoleAndPermission(DefaultRole defaultRole) {
        List<String> permissions = DefaultRole.Permission.getPermissionsByRole(defaultRole).stream().toList();
        return new Pair<>(defaultRole.getLabel(), permissions);
    }
}
