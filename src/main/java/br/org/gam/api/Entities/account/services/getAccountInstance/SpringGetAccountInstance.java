package br.org.gam.api.Entities.account.services.getAccountInstance;

import br.org.gam.api.Entities.account.AccountMapper;
import br.org.gam.api.Entities.account.Account;
import br.org.gam.api.Entities.account.exception.AccountNotFoundException;
import br.org.gam.api.Entities.account.myEmail.MyEmail;
import br.org.gam.api.Entities.account.persistence.AccountEntity;
import br.org.gam.api.Entities.account.persistence.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpringGetAccountInstance implements GetAccountInstance {

    private final AccountRepository accountRepo;
    private final AccountMapper accountMapper;

    public SpringGetAccountInstance(AccountRepository accountRepo, AccountMapper accountMapper) {
        this.accountRepo = accountRepo;
        this.accountMapper = accountMapper;
    }

    @Override
    public Account domainById(UUID id) {
        return accountRepo.findById(id)
                .map(accountMapper::entityToDomain)
                .orElseThrow(() -> new AccountNotFoundException("Could not find account with id " + id));

    }

    @Override
    public AccountEntity entityById(UUID id) {
        return accountRepo.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Could not find account with id " + id));
    }

    @Override
    public Account domainByEmail(MyEmail email) {
        return accountRepo.findByEmail(email)
                .map(accountMapper::entityToDomain)
                .orElseThrow(() -> new AccountNotFoundException("Could not find account with email " + email));
    }

    @Override
    public AccountEntity entityByEmail(MyEmail email) {
        return accountRepo.findByEmail(email)
                .orElseThrow(() -> new AccountNotFoundException("Could not find account with email " + email));
    }
}
