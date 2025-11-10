package br.org.gam.api.Entities.account.services.getAccountById;

import br.org.gam.api.Entities.account.IAccountMapper;
import br.org.gam.api.Entities.account.persistence.AccountEntity;
import br.org.gam.api.Entities.account.services.getAccountInstance.IGetAccountInstanceService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetAccountByIdService implements IGetAccountByIdService {

    private final IAccountMapper accountMapper;
    private final IGetAccountInstanceService getAccountInstanceService;

    public GetAccountByIdService(IAccountMapper accountMapper, IGetAccountInstanceService getAccountInstanceService) {
        this.accountMapper = accountMapper;
        this.getAccountInstanceService = getAccountInstanceService;
    }

    @Override
    public GetAccountByIdDTO getAccountById(UUID id) {

        AccountEntity accountEntity = getAccountInstanceService.getAccountEntityById(id);
        return accountMapper.fromEntityToGetAccountByIdDTO(accountEntity);
    }

}
