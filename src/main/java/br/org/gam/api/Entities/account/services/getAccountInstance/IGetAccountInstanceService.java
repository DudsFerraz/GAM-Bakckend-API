package br.org.gam.api.Entities.account.services.getAccountInstance;

import br.org.gam.api.Entities.account.Account;
import br.org.gam.api.Entities.account.persistence.AccountEntity;

import java.util.UUID;

public interface IGetAccountInstanceService {
    public Account getAccountDomainById(UUID id);
    public AccountEntity getAccountEntityById(UUID id);
}
