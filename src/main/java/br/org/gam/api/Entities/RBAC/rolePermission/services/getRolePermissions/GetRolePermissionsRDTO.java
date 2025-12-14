package br.org.gam.api.Entities.RBAC.rolePermission.services.getRolePermissions;

import br.org.gam.api.Entities.RBAC.permission.services.getPermission.GetPermissionRDTO;

import java.util.List;

public record GetRolePermissionsRDTO(
        List<GetPermissionRDTO> permissions
) {
}
