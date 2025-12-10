package br.org.gam.api.Entities.RBAC.accountRole.services.getAccountRoleInstance;

import br.org.gam.api.Entities.RBAC.accountRole.exception.AccountRoleNotFoundException;
import br.org.gam.api.Entities.RBAC.accountRole.persistence.AccountRoleEntity;
import br.org.gam.api.Entities.RBAC.accountRole.persistence.AccountRoleRepository;
import br.org.gam.api.Entities.RBAC.accountRole.services.AccountRoleDTO;
import org.springframework.stereotype.Service;

@Service
public class SpringGetAccountRoleInstance implements GetAccountRoleInstance {
    private final AccountRoleRepository accountRoleRepo;

    public SpringGetAccountRoleInstance(AccountRoleRepository accountRoleRepo) {
        this.accountRoleRepo = accountRoleRepo;
    }

    @Override
    public AccountRoleEntity entityByDTO(AccountRoleDTO dto) {

        return accountRoleRepo.findByAccount_IdAndRole_Id(dto.accountId(), dto.roleId())
                .orElseThrow(() ->  new AccountRoleNotFoundException(
                        String.format("Account with id: %s does not have role with id: %s",  dto.accountId(), dto.roleId())
                ));
    }
}
