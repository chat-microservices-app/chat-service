package com.chatapp.chatservice.enums;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public enum DefaultRole {

    ADMIN("ADMIN"),
    NORMIE("INVITED");


    private final String label;

    DefaultRole(String label) {
        this.label = label;
    }


    public String getLabel() {
        return label;
    }

    public static enum Permission {
        UPDATE("room.update", Set.of(ADMIN) ),

        READ("room.read", Set.of(ADMIN, NORMIE)),

        DELETE("room.delete", Set.of(ADMIN)),

        MUTATE("room.mutate", Set.of(ADMIN)),

        INVITE("room.invite", Set.of(ADMIN, NORMIE)),

        KICK("room.kick", Set.of(ADMIN)),
        UPDATE_MESSAGE("message.update", Set.of(ADMIN, NORMIE)),
        BAN("room.ban", Set.of(ADMIN));

        private final String label;

        private final Set<DefaultRole> defaultRole;

        Permission(String label, Set<DefaultRole> defaultRole) {
            this.label = label;
            this.defaultRole = defaultRole;
        }

        public String getLabel() {
            return label;
        }

        // if the role passed is in the set of roles, then that means the role has
        // the permission, so return the role, otherwise return null
        public DefaultRole getRole(DefaultRole defaultRole) {
            return this.defaultRole.contains(defaultRole) ? defaultRole : null;
        }

        public static Set<String> getPermissionsByRole(DefaultRole defaultRole) {
            Set<String> permissions = new HashSet<>();
            for (Permission permission : Permission.values()) {
                if (Objects.equals(permission.getRole(defaultRole), defaultRole)) {
                    permissions.add(permission.getLabel());
                }
            }
            return permissions;
        }


    }

}
