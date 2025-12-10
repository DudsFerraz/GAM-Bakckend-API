package br.org.gam.api.Entities.RBAC.permission;

import java.util.UUID;

public class Permission {
    private UUID id;
    private String name;
    private String description;

    Permission(UUID id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }


}
