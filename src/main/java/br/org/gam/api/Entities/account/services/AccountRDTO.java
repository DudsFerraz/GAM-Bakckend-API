package br.org.gam.api.Entities.account.services;

import br.org.gam.api.Entities.RBAC.accountRole.services.getAccountRoles.AccountRolesRDTO;
import br.org.gam.api.Entities.account.myEmail.MyEmail;

import java.util.UUID;

public record AccountRDTO(
        UUID id,
        MyEmail email,
        String displayName,
        AccountRolesRDTO roles
) {
}
