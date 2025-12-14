package br.org.gam.api.Entities.RBAC.role.services.getRole;

import java.util.UUID;

public record GetRoleRDTO(
        UUID id,
        String name,
        String description
) {
}
