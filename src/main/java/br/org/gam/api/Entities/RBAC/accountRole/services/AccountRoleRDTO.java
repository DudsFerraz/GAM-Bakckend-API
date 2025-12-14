package br.org.gam.api.Entities.RBAC.accountRole.services;

import br.org.gam.api.Entities.RBAC.role.services.getRole.GetRoleRDTO;
import br.org.gam.api.Entities.account.services.getAccount.GetAccountRDTO;

public record AccountRoleRDTO(
        GetAccountRDTO account,
        GetRoleRDTO role
) {
}
