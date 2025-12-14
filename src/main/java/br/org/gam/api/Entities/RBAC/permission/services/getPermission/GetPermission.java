package br.org.gam.api.Entities.RBAC.permission.services.getPermission;

import java.util.UUID;

public interface GetPermission {
    GetPermissionRDTO byId(UUID id);
}
