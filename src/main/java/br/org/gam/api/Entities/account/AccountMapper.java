package br.org.gam.api.Entities.account;

import br.org.gam.api.Entities.account.services.registerAccount.RegisterAccountRDTO;
import br.org.gam.api.Entities.account.persistence.AccountEntity;
import br.org.gam.api.Entities.account.services.getAccountById.GetAccountByIdDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountEntity fromDomainToEntity(Account account);
    Account fromEntityToDomain(AccountEntity accountEntity);
    RegisterAccountRDTO fromEntityToRegisterAccountResponseDTO(AccountEntity accountEntity);
    GetAccountByIdDTO fromEntityToGetAccountByIdDTO(AccountEntity accountEntity);
}