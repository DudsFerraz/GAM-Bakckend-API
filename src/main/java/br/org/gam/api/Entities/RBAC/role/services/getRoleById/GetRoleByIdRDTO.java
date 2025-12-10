package br.org.gam.api.Entities.RBAC.role.services.getRoleById;

import java.util.UUID;

public record GetRoleByIdRDTO(
        UUID id,
        String name,
        String description
) {
}
