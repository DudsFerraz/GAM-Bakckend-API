package br.org.gam.api.Entities.RBAC.accountRole.services.getAccountRoleInstance;

import br.org.gam.api.Entities.RBAC.accountRole.persistence.AccountRoleEntity;
import br.org.gam.api.Entities.RBAC.accountRole.services.AccountRoleDTO;

public interface GetAccountRoleInstance {
    AccountRoleEntity entityByDTO(AccountRoleDTO dto);
}
