package br.org.gam.api.account.application;

import br.org.gam.api.shared.domain.GamEmail;
import br.org.gam.api.account.persistence.AccountEntity;
import br.org.gam.api.account.persistence.AccountRepository;
import br.org.gam.api.shared.exception.NotFoundException;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class AccountEntityLoader {

    private final AccountRepository accountRepo;

    public AccountEntityLoader(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    public AccountEntity requiredById(UUID id) {
        return accountRepo.findById(id)
                .orElseThrow(() -> NotFoundException.resource("Account", id));
    }

    public AccountEntity requiredByEmail(GamEmail email) {
        return accountRepo.findByEmail(email)
                .orElseThrow(() -> NotFoundException.resource("Account", email));
    }
}
