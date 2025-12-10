package br.org.gam.api.Entities.RBAC.accountRole.services.addAccountRole;

import br.org.gam.api.Entities.RBAC.accountRole.services.AccountRoleDTO;
import br.org.gam.api.Entities.RBAC.accountRole.services.AccountRoleRDTO;

import java.util.UUID;

public interface AddAccountRole {
    AccountRoleRDTO byDTO(AccountRoleDTO dto);
    AccountRoleRDTO byRoleName(String roleName, UUID accountId);
}
