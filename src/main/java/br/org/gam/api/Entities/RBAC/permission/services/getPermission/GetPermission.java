package br.org.gam.api.Entities.RBAC.permission.services.getPermission;

import br.org.gam.api.Entities.RBAC.permission.services.PermissionRDTO;

import java.util.UUID;

public interface GetPermission {
    PermissionRDTO byId(UUID id);
}
