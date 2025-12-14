package br.org.gam.api.Entities.account.services.getAccount;

import br.org.gam.api.Entities.RBAC.accountRole.services.getAccountRoles.GetAccountRoles;
import br.org.gam.api.Entities.RBAC.accountRole.services.getAccountRoles.GetAccountRolesRDTO;
import br.org.gam.api.Entities.account.AccountMapper;
import br.org.gam.api.Entities.account.persistence.AccountEntity;
import br.org.gam.api.Entities.account.services.getAccountInstance.GetAccountInstance;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpringGetAccount implements GetAccount {

    private final AccountMapper accountMapper;
    private final GetAccountInstance getAccountInstance;
    private final GetAccountRoles getAccountRoles;

    public SpringGetAccount(AccountMapper accountMapper, GetAccountInstance getAccountInstance, GetAccountRoles getAccountRoles) {
        this.accountMapper = accountMapper;
        this.getAccountInstance = getAccountInstance;
        this.getAccountRoles = getAccountRoles;
    }

    @Override
    public GetAccountRDTO byId(UUID id) {

        AccountEntity accountEntity = getAccountInstance.entityById(id);

        GetAccountRolesRDTO rolesDto = getAccountRoles.get(accountEntity.getId());

        return accountMapper.fromEntityToGetAccountRDTO(accountEntity, rolesDto);
    }

}
