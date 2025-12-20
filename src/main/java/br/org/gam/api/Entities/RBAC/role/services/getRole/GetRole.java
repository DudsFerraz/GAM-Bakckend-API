package br.org.gam.api.Entities.RBAC.role.services.getRole;

import br.org.gam.api.Entities.RBAC.role.services.RoleRDTO;

import java.util.UUID;

public interface GetRole {
    RoleRDTO byId(UUID id);
}
