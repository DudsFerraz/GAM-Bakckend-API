package br.org.gam.api.Entities.RBAC.permission.services.getPermissionById;

import java.util.UUID;

public interface GetPermissionById {
    GetPermissionByIdRDTO GetPermissionById(UUID id);
}
