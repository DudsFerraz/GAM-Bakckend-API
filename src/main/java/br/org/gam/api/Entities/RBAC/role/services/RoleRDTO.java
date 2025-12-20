package br.org.gam.api.Entities.RBAC.role.services;

import java.util.UUID;

public record RoleRDTO(
        UUID id,
        String name,
        String description
) {
}
