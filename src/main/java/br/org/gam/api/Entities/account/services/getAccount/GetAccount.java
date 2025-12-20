package br.org.gam.api.Entities.account.services.getAccount;

import br.org.gam.api.Entities.account.services.AccountRDTO;

import java.util.UUID;

public interface GetAccount {
    AccountRDTO byId(UUID id);
}
