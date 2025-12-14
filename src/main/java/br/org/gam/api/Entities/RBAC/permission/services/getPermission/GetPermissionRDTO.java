package br.org.gam.api.Entities.RBAC.permission.services.getPermission;

import java.util.UUID;

public record GetPermissionRDTO(
        UUID id,
        String name,
        String description
) {
}
