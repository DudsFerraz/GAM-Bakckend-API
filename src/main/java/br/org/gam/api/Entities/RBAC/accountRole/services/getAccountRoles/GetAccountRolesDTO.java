package br.org.gam.api.Entities.RBAC.accountRole.services.getAccountRoles;

import br.org.gam.api.Entities.RBAC.role.services.getRoleById.GetRoleByIdDTO;

import java.util.List;
import java.util.UUID;

public record GetAccountRolesDTO(
        List<GetRoleByIdDTO> roles
) {
}
