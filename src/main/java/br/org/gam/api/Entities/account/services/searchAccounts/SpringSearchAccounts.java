package br.org.gam.api.Entities.account.services.searchAccounts;

import br.org.gam.api.Entities.RBAC.accountRole.services.getAccountRoles.GetAccountRoles;
import br.org.gam.api.Entities.RBAC.accountRole.services.getAccountRoles.GetAccountRolesRDTO;
import br.org.gam.api.Entities.account.AccountMapper;
import br.org.gam.api.Entities.account.persistence.AccountEntity;
import br.org.gam.api.Entities.account.persistence.AccountRepository;
import br.org.gam.api.Entities.account.services.getAccount.GetAccountRDTO;
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
    private final GetAccountRoles getAccountRoles;

    public SpringSearchAccounts(AccountRepository accountRepo, AccountMapper accountMapper, GetAccountRoles getAccountRoles) {
        this.accountRepo = accountRepo;
        this.accountMapper = accountMapper;
        this.getAccountRoles = getAccountRoles;
    }

    @Override
    public Page<GetAccountRDTO> search(List<SpecificationFilter> filters, Pageable pageable) {
        Specification<AccountEntity> spec = SpecificationBuilder.build(filters);

        Page<AccountEntity> entitiesPage = accountRepo.findAll(spec, pageable);

        return entitiesPage
                .map(acc -> {
                    GetAccountRolesRDTO accountRolesRDTO = getAccountRoles.get(acc.getId());
                    return accountMapper.fromEntityToGetAccountRDTO(acc, accountRolesRDTO);
                });
    }

}
