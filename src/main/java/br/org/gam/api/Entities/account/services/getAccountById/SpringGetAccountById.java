package br.org.gam.api.Entities.account.services.getAccountById;

import br.org.gam.api.Entities.account.AccountMapper;
import br.org.gam.api.Entities.account.persistence.AccountEntity;
import br.org.gam.api.Entities.account.services.getAccountInstance.GetAccountInstance;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpringGetAccountById implements GetAccountById {

    private final AccountMapper accountMapper;
    private final GetAccountInstance getAccountInstance;

    public SpringGetAccountById(AccountMapper accountMapper, GetAccountInstance getAccountInstance) {
        this.accountMapper = accountMapper;
        this.getAccountInstance = getAccountInstance;
    }

    @Override
    public GetAccountByIdDTO getAccountById(UUID id) {

        AccountEntity accountEntity = getAccountInstance.getAccountEntityById(id);
        return accountMapper.fromEntityToGetAccountByIdDTO(accountEntity);
    }

}
