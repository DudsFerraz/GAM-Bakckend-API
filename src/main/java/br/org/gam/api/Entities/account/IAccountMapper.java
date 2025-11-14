package br.org.gam.api.Entities.account;

import br.org.gam.api.Entities.account.services.loginAccount.LoginAccountResponseDTO;
import br.org.gam.api.Entities.account.services.registerAccount.RegisterAccountResponseDTO;
import br.org.gam.api.Entities.account.persistence.AccountEntity;
import br.org.gam.api.Entities.account.services.getAccountById.GetAccountByIdDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IAccountMapper {
    AccountEntity fromDomainToEntity(Account account);
    Account fromEntityToDomain(AccountEntity accountEntity);
    RegisterAccountResponseDTO fromEntityToRegisterAccountResponseDTO(AccountEntity accountEntity);
    GetAccountByIdDTO fromEntityToGetAccountByIdDTO(AccountEntity accountEntity);
}