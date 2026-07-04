package br.org.gam.api.rbac.role.domain;

import lombok.Getter;

@Getter
public enum SystemRole {
    SUDO("SUDO", "Super user with unrestricted system access"),
    COORD("COORD", "Coordinator with administrative access to GAM operations"),
    MEMBER("MEMBER", "Volunteer member with standard authenticated access"),
    VISITOR("VISITOR", "Visitor with limited public access");

    private final String code;
    private final String description;

    SystemRole(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
