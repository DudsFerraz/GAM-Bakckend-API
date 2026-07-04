package br.org.gam.api.rbac.role.application.useCases.getrolePermissions;

import br.org.gam.api.rbac.permission.application.PermissionRDTO;
import java.util.List;

public record GetRolePermissionsRDTO(
        List<PermissionRDTO> permissions
) {
}
