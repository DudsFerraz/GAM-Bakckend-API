package br.org.gam.api.Entities.RBAC.accountRole.services;

import br.org.gam.api.Entities.RBAC.role.services.RoleRDTO;
import br.org.gam.api.Entities.account.services.AccountRDTO;

public record AccountRoleRDTO(
        AccountRDTO account,
        RoleRDTO role
) {
}
