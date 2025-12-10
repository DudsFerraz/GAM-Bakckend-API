package br.org.gam.api.Entities.account.services.getAccount;

import br.org.gam.api.Entities.account.MyEmail;

import java.util.UUID;

public record GetAccountRDTO(
        UUID id,
        MyEmail email,
        String displayName
) {
}
