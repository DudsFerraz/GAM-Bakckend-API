package br.org.gam.api.rbac.accountRole.application;

import br.org.gam.api.account.application.AccountMapper;
import br.org.gam.api.rbac.accountRole.persistence.AccountRoleEntity;
import br.org.gam.api.rbac.role.application.RoleMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {AccountMapper.class, RoleMapper.class})
public interface AccountRoleMapper {

    // =====================================================================================
    // Persistence -> RDTO
    // =====================================================================================

    AccountRoleRDTO entityToRDTO(AccountRoleEntity accountRoleEntity);
}
