package br.org.gam.api.Entities.RBAC.accountRole.services.getAccountRoles;

import br.org.gam.api.Entities.RBAC.role.services.getRole.GetRoleRDTO;

import java.util.List;

public record GetAccountRolesRDTO(
        List<GetRoleRDTO> roles
) {
}
