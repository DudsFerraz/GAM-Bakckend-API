package br.org.gam.api.Entities.RBAC.role.services.getRolePermissions;

import java.util.UUID;

public interface GetRolePermissions {
    GetRolePermissionsRDTO allById(UUID roleId);
}
