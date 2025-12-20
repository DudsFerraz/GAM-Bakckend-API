package br.org.gam.api.Entities.account.services.getAccount;

import br.org.gam.api.Entities.account.AccountMapper;
import br.org.gam.api.Entities.account.persistence.AccountEntity;
import br.org.gam.api.Entities.account.services.AccountRDTO;
import br.org.gam.api.Entities.account.services.getAccountInstance.GetAccountInstance;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class SpringGetAccount implements GetAccount {

    private final AccountMapper accountMapper;
    private final GetAccountInstance getAccountInstance;

    public SpringGetAccount(AccountMapper accountMapper, GetAccountInstance getAccountInstance) {
        this.accountMapper = accountMapper;
        this.getAccountInstance = getAccountInstance;
    }

    @Transactional(readOnly = true)
    @Override
    public AccountRDTO byId(UUID id) {

        AccountEntity accountEntity = getAccountInstance.entityById(id);
        return accountMapper.entityToAccountRDTO(accountEntity);
    }

}
