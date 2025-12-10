package br.org.gam.api.Entities.RBAC.accountRole.services.getAccountRoles;

import br.org.gam.api.Entities.RBAC.role.services.getRoleById.GetRoleByIdRDTO;

import java.util.List;

public record GetAccountRolesRDTO(
        List<GetRoleByIdRDTO> roles
) {
}
