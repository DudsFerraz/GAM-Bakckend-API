package br.org.gam.api.Entities.account.services.createAccount.service;

import br.org.gam.api.Entities.account.services.createAccount.dto.CreateAccountDTO;
import br.org.gam.api.Entities.account.services.createAccount.dto.CreateAccountResponseDTO;

public interface ICreateAccountService {
    public CreateAccountResponseDTO createAccount(CreateAccountDTO dto);
}
