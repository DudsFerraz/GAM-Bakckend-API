package br.org.gam.api.Entities.account.services.changePermissionLevel;

import br.org.gam.api.Entities.account.IAccountMapper;
import br.org.gam.api.Entities.account.Account;
import br.org.gam.api.Entities.account.persistence.AccountEntity;
import br.org.gam.api.Entities.account.persistence.IAccountRepository;
import br.org.gam.api.Entities.account.services.getAccountInstance.IGetAccountInstanceService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Consumer;

@Service
public class ChangePermissionLevelService implements IChangePermissionLevelService{

    private final IAccountRepository accountRepo;
    private final IAccountMapper accountMapper;
    private final IGetAccountInstanceService getAccountInstanceService;

    public ChangePermissionLevelService(IAccountRepository accountRepo, IAccountMapper accountMapper, IGetAccountInstanceService getAccountInstanceService) {
        this.accountRepo = accountRepo;
        this.accountMapper = accountMapper;
        this.getAccountInstanceService = getAccountInstanceService;
    }

    @Transactional
    @Override
    public void setToVisitor(UUID accountId) {
        changePermissionLevel(accountId, Account::setPermissionLevelToVisitor);
    }

    @Transactional
    @Override
    public void setToMember(UUID accountId) {
        changePermissionLevel(accountId, Account::setPermissionLevelToMember);
    }

    @Transactional
    @Override
    public void setToCoord(UUID accountId) {
        changePermissionLevel(accountId, Account::setPermissionLevelToCoord);
    }

    private void changePermissionLevel(UUID accountId, Consumer<Account> consumer) {
        Account account = getAccountInstanceService.getAccountDomainById(accountId);
        consumer.accept(account);

        AccountEntity accountEntity = accountMapper.fromDomainToEntity(account);
        accountRepo.save(accountEntity);
    }

}
