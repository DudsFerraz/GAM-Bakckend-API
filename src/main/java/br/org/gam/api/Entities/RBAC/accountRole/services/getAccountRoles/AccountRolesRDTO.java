package br.org.gam.api.Entities.RBAC.accountRole.services.getAccountRoles;

import br.org.gam.api.Entities.RBAC.role.services.RoleRDTO;

import java.util.ArrayList;
import java.util.List;

public record AccountRolesRDTO(
        List<RoleRDTO> roles
) {
    public AccountRolesRDTO() {
        this(new ArrayList<>());
    }
}
