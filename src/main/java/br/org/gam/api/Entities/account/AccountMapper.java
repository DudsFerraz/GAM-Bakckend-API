package br.org.gam.api.Entities.account;

import br.org.gam.api.Entities.RBAC.accountRole.AccountRoleMapper;
import br.org.gam.api.Entities.RBAC.accountRole.persistence.AccountRoleEntity;
import br.org.gam.api.Entities.RBAC.accountRole.services.getAccountRoles.GetAccountRolesRDTO;
import br.org.gam.api.Entities.RBAC.role.RoleMapper;
import br.org.gam.api.Entities.RBAC.role.persistence.RoleEntity;
import br.org.gam.api.Entities.RBAC.role.services.getRole.GetRoleRDTO;
import br.org.gam.api.Entities.account.services.getAccount.GetAccountRDTO;
import br.org.gam.api.Entities.account.services.registerAccount.RegisterAccountRDTO;
import br.org.gam.api.Entities.account.persistence.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", uses = { RoleMapper.class })
public interface AccountMapper {
    AccountEntity fromDomainToEntity(Account account);
    Account fromEntityToDomain(AccountEntity accountEntity);
    RegisterAccountRDTO fromEntityToRegisterAccountRDTO(AccountEntity accountEntity);

    @Mapping(target = "roles", source = "accountRoles", qualifiedByName = "mapAccountRolesToDTO")
    GetAccountRDTO fromEntityToGetAccountRDTO(AccountEntity accountEntity);

    @Named("accountHelperToRoleRDTO")
    GetRoleRDTO toRoleRDTO(RoleEntity roleEntity);

    @Named("mapAccountRolesToDTO")
    default GetAccountRolesRDTO mapAccountRolesToDTO(Collection<AccountRoleEntity> accountRoles) {
        if (accountRoles == null || accountRoles.isEmpty()) {
            return new  GetAccountRolesRDTO();
        }

        List<GetRoleRDTO> rolesDtos = accountRoles
                .stream()
                .map(AccountRoleEntity::getRole)
                .map(this::toRoleRDTO)
                .toList();

        return new GetAccountRolesRDTO(rolesDtos);
    }
}