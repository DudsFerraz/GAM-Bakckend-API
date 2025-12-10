package br.org.gam.api.Entities.RBAC.permission.services.getPermissionById;

import java.util.UUID;

public record GetPermissionByIdRDTO(
        UUID id,
        String name,
        String description
) {
}
