package br.org.gam.api.Entities.account.services.getAccountInstance;

import br.org.gam.api.Entities.account.IAccountMapper;
import br.org.gam.api.Entities.account.Account;
import br.org.gam.api.Entities.account.exception.AccountNotFoundException;
import br.org.gam.api.Entities.account.persistence.AccountEntity;
import br.org.gam.api.Entities.account.persistence.IAccountRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetAccountInstanceService implements IGetAccountInstanceService {

    private final IAccountRepository accountRepo;
    private final IAccountMapper accountMapper;

    public GetAccountInstanceService(IAccountRepository accountRepo, IAccountMapper accountMapper) {
        this.accountRepo = accountRepo;
        this.accountMapper = accountMapper;
    }

    @Override
    public Account getAccountDomainById(UUID id) {
        return accountRepo.findById(id)
                .map(accountMapper::fromEntityToDomain)
                .orElseThrow(() -> new AccountNotFoundException("Could not find account with id " + id));

    }

    @Override
    public AccountEntity getAccountEntityById(UUID id) {
        return accountRepo.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Could not find account with id " + id));
    }
}
