package br.org.gam.api.Entities.account.services.getAccount;

import br.org.gam.api.Entities.RBAC.accountRole.services.getAccountRoles.GetAccountRolesRDTO;
import br.org.gam.api.Entities.account.myEmail.MyEmail;

import java.util.UUID;

public record GetAccountRDTO(
        UUID id,
        MyEmail email,
        String displayName,
        GetAccountRolesRDTO roles
) {
}
