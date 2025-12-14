package br.org.gam.api.Entities.RBAC.rolePermission.services.getRolePermissions;

import java.util.UUID;

public interface GetRolePermission {
    GetRolePermissionsRDTO allById(UUID roleId);
}
