package br.org.gam.api.Entities.account.common;

import br.org.gam.api.Entities.account.domain.Account;
import br.org.gam.api.Entities.account.services.createAccount.dto.CreateAccountResponseDTO;
import br.org.gam.api.Entities.account.persistence.AccountEntity;
import br.org.gam.api.Entities.account.services.getAccountById.dto.GetAccountByIdDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IAccountMapper {

    AccountEntity fromDomainToEntity(Account account);

    Account fromEntityToDomain(AccountEntity accountEntity);

    CreateAccountResponseDTO fromEntityToCreateAccountResponseDTO(AccountEntity accountEntity);

    GetAccountByIdDTO fromEntityToGetAccountByIdDTO(AccountEntity accountEntity);
}