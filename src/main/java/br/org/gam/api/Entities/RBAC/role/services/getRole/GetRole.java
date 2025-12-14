package br.org.gam.api.Entities.RBAC.role.services.getRole;

import java.util.UUID;

public interface GetRole {
    GetRoleRDTO byId(UUID id);
}
