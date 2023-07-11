package com.chatapp.chatservice.enums;

import java.util.HashSet;
import java.util.Set;

public enum Role {

    ADMIN("ADMIN");


    private final String label;

    Role(String label) {
        this.label = label;
    }


    public String getLabel() {
        return label;
    }

    public static enum Permission {
        UPDATE("room.update", ADMIN),

        DELETE("room.delete", ADMIN);

        private final String label;

        private final Role role;

        Permission(String label, Role role) {
            this.label = label;
            this.role = role;
        }

        public String getLabel() {
            return label;
        }

        public Role getRole() {
            return role;
        }

        public static Set<String> getPermissionsByRole(Role role) {
            Set<String> permissions = new HashSet<>();
            for (Permission permission : Permission.values()) {
                if (permission.getRole().equals(role)) {
                    permissions.add(permission.getLabel());
                }
            }
            return permissions;
        }


    }

}
