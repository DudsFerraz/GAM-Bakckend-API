package br.org.gam.api.Entities.account.services.getAccount;

import java.util.UUID;

public interface GetAccount {
    GetAccountRDTO byId(UUID id);
}
