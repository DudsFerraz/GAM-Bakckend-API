package br.org.gam.api.Entities.account;

import br.org.gam.api.Entities.account.services.getAccount.GetAccountRDTO;
import br.org.gam.api.Entities.account.services.registerAccount.RegisterAccountRDTO;
import br.org.gam.api.Entities.account.persistence.AccountEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountEntity fromDomainToEntity(Account account);
    Account fromEntityToDomain(AccountEntity accountEntity);
    RegisterAccountRDTO fromEntityToRegisterAccountRDTO(AccountEntity accountEntity);
    GetAccountRDTO fromEntityToGetAccountRDTO(AccountEntity accountEntity);
}