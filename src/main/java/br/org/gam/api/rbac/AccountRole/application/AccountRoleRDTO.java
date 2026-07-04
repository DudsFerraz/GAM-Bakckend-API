package br.org.gam.api.rbac.accountRole.application;

import br.org.gam.api.account.application.AccountRDTO;
import br.org.gam.api.rbac.role.application.RoleRDTO;

public record AccountRoleRDTO(
        AccountRDTO account,
        RoleRDTO role
) {
}
