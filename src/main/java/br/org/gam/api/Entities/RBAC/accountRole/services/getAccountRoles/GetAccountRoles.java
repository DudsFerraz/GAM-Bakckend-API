package br.org.gam.api.Entities.RBAC.accountRole.services.getAccountRoles;

import java.util.UUID;

public interface GetAccountRoles {
    AccountRolesRDTO get(UUID accountId);
}
