package br.org.gam.api.Entities.account.services.getAccountInstance;

import br.org.gam.api.Entities.account.Account;
import br.org.gam.api.Entities.account.myEmail.MyEmail;
import br.org.gam.api.Entities.account.persistence.AccountEntity;

import java.util.UUID;

public interface GetAccountInstance {
    Account domainById(UUID id);
    AccountEntity entityById(UUID id);
    Account domainByEmail(MyEmail email);
    AccountEntity entityByEmail(MyEmail email);
}
