package br.org.gam.api.Entities.account.services.getAccountById.service;

import br.org.gam.api.Entities.account.common.IAccountMapper;
import br.org.gam.api.Entities.account.exception.AccountNotFoundException;
import br.org.gam.api.Entities.account.persistence.AccountEntity;
import br.org.gam.api.Entities.account.persistence.IAccountRepository;
import br.org.gam.api.Entities.account.services.getAccountById.dto.GetAccountByIdDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetAccountByIdService implements IGetAccountByIdService {

    private final IAccountRepository accountRepo;
    private final IAccountMapper accountMapper;

    public GetAccountByIdService(IAccountRepository accountRepo, IAccountMapper accountMapper) {
        this.accountRepo = accountRepo;
        this.accountMapper = accountMapper;
    }

    @Override
    public GetAccountByIdDTO getAccountById(UUID id) {
        AccountEntity entity = accountRepo.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Could not find account with id " + id) );

        return accountMapper.fromEntityToGetAccountDTO(entity);
    }
}
