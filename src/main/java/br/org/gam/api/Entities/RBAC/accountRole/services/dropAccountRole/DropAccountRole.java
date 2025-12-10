package br.org.gam.api.Entities.RBAC.accountRole.services.dropAccountRole;

import br.org.gam.api.Entities.RBAC.accountRole.services.AccountRoleDTO;

import java.util.UUID;

public interface DropAccountRole {
    void byDTO(AccountRoleDTO dto);
    void byRoleName(String roleName, UUID accountId);
}
