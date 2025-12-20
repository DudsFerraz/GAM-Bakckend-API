package br.org.gam.api.Entities.account;

import br.org.gam.api.Entities.RBAC.accountRole.persistence.AccountRoleEntity;
import br.org.gam.api.Entities.RBAC.accountRole.services.getAccountRoles.AccountRolesRDTO;
import br.org.gam.api.Entities.RBAC.role.RoleMapper;
import br.org.gam.api.Entities.RBAC.role.services.RoleRDTO;
import br.org.gam.api.Entities.account.services.AccountRDTO;
import br.org.gam.api.Entities.account.services.registerAccount.RegisterAccountRDTO;
import br.org.gam.api.Entities.account.persistence.AccountEntity;
import br.org.gam.api.common.auditing.IgnoreFullAuditFields;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", uses = { RoleMapper.class })
public interface AccountMapper {
    @IgnoreFullAuditFields
    AccountEntity domainToEntity(Account account);
    Account entityToDomain(AccountEntity accountEntity);
    RegisterAccountRDTO entityToRegisterAccountRDTO(AccountEntity accountEntity);

    @Mapping(target = "roles", source = "accountRoles", qualifiedByName = "accountRolesToAccountRolesRDTO")
    AccountRDTO entityToAccountRDTO(AccountEntity accountEntity);

    @Mapping(source = "role", target = ".")
    RoleRDTO accountRoleToRoleRDTO(AccountRoleEntity accountRoleEntity);

    List<RoleRDTO> accountRolesToRoleRDTOs(Collection<AccountRoleEntity> accountRoles);

    @Named("accountRolesToAccountRolesRDTO")
    default AccountRolesRDTO accountRolesToAccountRolesRDTO(Collection<AccountRoleEntity> accountRoles) {
        if (accountRoles == null) {
            return new AccountRolesRDTO();
        }
        return new AccountRolesRDTO(accountRolesToRoleRDTOs(accountRoles));
    }
}