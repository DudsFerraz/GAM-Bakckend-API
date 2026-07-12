package br.org.gam.api.rbac.role.domain;

import lombok.Getter;

@Getter
public enum SystemRole {
    SUDO("SUDO", "Developer-controlled unrestricted system access"),
    COORD("COORD", "Coordinator access to GAM operational administration"),
    MEMBER("MEMBER", "Standard authenticated member access"),
    VISITOR("VISITOR", "No baseline permission; public visibility is represented by a null event requiredPermissionId");

    private final String code;
    private final String description;

    SystemRole(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
