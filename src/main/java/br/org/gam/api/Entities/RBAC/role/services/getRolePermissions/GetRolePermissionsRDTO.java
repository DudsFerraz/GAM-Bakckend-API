package br.org.gam.api.Entities.RBAC.role.services.getRolePermissions;

import br.org.gam.api.Entities.RBAC.permission.services.PermissionRDTO;

import java.util.List;

public record GetRolePermissionsRDTO(
        List<PermissionRDTO> permissions
) {
}
