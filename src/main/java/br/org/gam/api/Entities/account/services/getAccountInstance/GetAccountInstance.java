package br.org.gam.api.Entities.account.services.getAccountInstance;

import br.org.gam.api.Entities.account.Account;
import br.org.gam.api.Entities.account.persistence.AccountEntity;

import java.util.UUID;

public interface GetAccountInstance {
    public Account domainById(UUID id);
    public AccountEntity entityById(UUID id);
}
