package br.org.gam.api.Entities.RBAC.rolePermission.services.getRolePermissions;

import br.org.gam.api.Entities.RBAC.permission.services.getPermissionById.GetPermissionByIdRDTO;
import br.org.gam.api.Entities.RBAC.rolePermission.persistence.RolePermissionEntity;

import java.util.List;

public record GetRolePermissionsRDTO(
        List<GetPermissionByIdRDTO> permissions
) {
}
