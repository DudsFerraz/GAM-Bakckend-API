package br.org.gam.api.Entities.account.services.searchAccounts;

import br.org.gam.api.Entities.account.AccountMapper;
import br.org.gam.api.Entities.account.persistence.AccountEntity;
import br.org.gam.api.Entities.account.persistence.AccountRepository;
import br.org.gam.api.Entities.account.services.getAccountById.GetAccountByIdDTO;
import br.org.gam.api.common.specification.SpecificationBuilder;
import br.org.gam.api.common.specification.SpecificationFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpringSearchAccounts implements SearchAccounts {

    private final AccountRepository accountRepo;
    private final AccountMapper accountMapper;

    public SpringSearchAccounts(AccountRepository accountRepo, AccountMapper accountMapper) {
        this.accountRepo = accountRepo;
        this.accountMapper = accountMapper;
    }

    @Override
    public Page<GetAccountByIdDTO> searchAccounts(List<SpecificationFilter> filters, Pageable pageable) {
        Specification<AccountEntity> spec = SpecificationBuilder.build(filters);

        Page<AccountEntity> entitiesPage = accountRepo.findAll(spec, pageable);

        return entitiesPage.map(accountMapper::fromEntityToGetAccountByIdDTO);
    }


}
