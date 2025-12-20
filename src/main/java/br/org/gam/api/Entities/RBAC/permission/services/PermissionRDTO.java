package br.org.gam.api.Entities.RBAC.permission.services;

import java.util.UUID;

public record PermissionRDTO(
        UUID id,
        String name,
        String description
) {
}
