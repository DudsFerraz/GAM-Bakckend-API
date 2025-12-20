package br.org.gam.api.Entities.RBAC.accountRole;

import br.org.gam.api.Entities.RBAC.accountRole.persistence.AccountRoleEntity;
import br.org.gam.api.Entities.RBAC.accountRole.services.AccountRoleRDTO;
import br.org.gam.api.Entities.RBAC.role.RoleMapper;
import br.org.gam.api.Entities.account.AccountMapper;
import br.org.gam.api.common.auditing.IgnoreJunctionAuditFields;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {AccountMapper.class, RoleMapper.class})
public interface AccountRoleMapper {
    @IgnoreJunctionAuditFields
    AccountRoleEntity domainToEntity(AccountRole newAccountRole);
    AccountRoleRDTO entityToAccountRoleRDTO(AccountRoleEntity accountRoleEntity);
}
