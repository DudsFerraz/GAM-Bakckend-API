package br.org.gam.api.Entities.RBAC.rolePermission.services.getRolePermissions;

import java.util.UUID;

public interface GetRolePermissions {
    GetRolePermissionsRDTO getRolePermissions(UUID roleId);
}
