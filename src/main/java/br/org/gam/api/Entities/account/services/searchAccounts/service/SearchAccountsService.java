package br.org.gam.api.Entities.account.services.searchAccounts.service;

import br.org.gam.api.Entities.account.common.IAccountMapper;
import br.org.gam.api.Entities.account.persistence.AccountEntity;
import br.org.gam.api.Entities.account.persistence.IAccountRepository;
import br.org.gam.api.Entities.account.services.getAccountById.dto.GetAccountByIdDTO;
import br.org.gam.api.common.specification.SpecificationBuilder;
import br.org.gam.api.common.specification.SpecificationFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchAccountsService implements ISearchAccountsService {

    private final IAccountRepository accountRepo;
    private final IAccountMapper accountMapper;

    public SearchAccountsService(IAccountRepository accountRepo, IAccountMapper accountMapper) {
        this.accountRepo = accountRepo;
        this.accountMapper = accountMapper;
    }

    @Override
    public Page<GetAccountByIdDTO> getAccounts(List<SpecificationFilter> filters, Pageable pageable) {
        Specification<AccountEntity> spec = SpecificationBuilder.build(filters);

        Page<AccountEntity> entitiesPage = accountRepo.findAll(spec, pageable);

        return entitiesPage.map(accountMapper::fromEntityToGetAccountDTO);
    }


}
