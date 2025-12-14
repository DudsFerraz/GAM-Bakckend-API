package br.org.gam.api.Entities.account;

import br.org.gam.api.Entities.RBAC.accountRole.services.getAccountRoles.GetAccountRolesRDTO;
import br.org.gam.api.Entities.account.services.getAccount.GetAccountRDTO;
import br.org.gam.api.Entities.account.services.registerAccount.RegisterAccountRDTO;
import br.org.gam.api.Entities.account.persistence.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountEntity fromDomainToEntity(Account account);
    Account fromEntityToDomain(AccountEntity accountEntity);
    RegisterAccountRDTO fromEntityToRegisterAccountRDTO(AccountEntity accountEntity);

    @Mapping(target = "roles", source = "rolesDto")
    GetAccountRDTO fromEntityToGetAccountRDTO(AccountEntity accountEntity, GetAccountRolesRDTO rolesDto);
}