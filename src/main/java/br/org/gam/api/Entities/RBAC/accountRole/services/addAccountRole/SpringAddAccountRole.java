package br.org.gam.api.Entities.RBAC.accountRole.services.addAccountRole;

import br.org.gam.api.Entities.RBAC.accountRole.AccountRole;
import br.org.gam.api.Entities.RBAC.accountRole.AccountRoleMapper;
import br.org.gam.api.Entities.RBAC.accountRole.exception.AccountAlreadyHasRoleException;
import br.org.gam.api.Entities.RBAC.accountRole.persistence.AccountRoleEntity;
import br.org.gam.api.Entities.RBAC.accountRole.persistence.AccountRoleRepository;
import br.org.gam.api.Entities.RBAC.accountRole.services.AccountRoleDTO;
import br.org.gam.api.Entities.RBAC.accountRole.services.AccountRoleRDTO;
import br.org.gam.api.Entities.RBAC.role.Role;
import br.org.gam.api.Entities.RBAC.role.services.getRoleInstance.GetRoleInstance;
import br.org.gam.api.Entities.account.Account;
import br.org.gam.api.Entities.account.services.getAccountInstance.GetAccountInstance;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpringAddAccountRole implements AddAccountRole {
    private final AccountRoleRepository accountRoleRepo;
    private final GetAccountInstance getAccountInstance;
    private final GetRoleInstance getRoleInstance;
    private final AccountRoleMapper accountRoleMapper;

    public SpringAddAccountRole(AccountRoleRepository accountRoleRepo, GetAccountInstance getAccountInstance, GetRoleInstance getRoleInstance, AccountRoleMapper accountRoleMapper) {
        this.accountRoleRepo = accountRoleRepo;
        this.getAccountInstance = getAccountInstance;
        this.getRoleInstance = getRoleInstance;
        this.accountRoleMapper = accountRoleMapper;
    }

    @Transactional
    @Override
    public AccountRoleRDTO byDTO(AccountRoleDTO dto) {

        Account account = getAccountInstance.domainById(dto.accountId());
        Role role = getRoleInstance.domainById(dto.roleId());

        if (accountRoleRepo.existsByAccount_IdAndRole_Id(account.getId(), role.getId())) {
            throw new AccountAlreadyHasRoleException(
                    String.format("Account: %s already has role: %s", account.getEmail(), role.getName()));
        }

        AccountRole newAccountRole = AccountRole.register(account, role);
        AccountRoleEntity newAccountRoleEntity = accountRoleMapper.fromDomainToEntity(newAccountRole);
        AccountRoleEntity savedAccountRoleEntity = accountRoleRepo.save(newAccountRoleEntity);

        return accountRoleMapper.fromEntityToAccountRoleRDTO(savedAccountRoleEntity);
    }

    @Transactional
    @Override
    public AccountRoleRDTO byRoleName(String roleName, UUID accountId) {
        UUID roleId = getRoleInstance.entityByName(roleName).getId();

        return byDTO(new AccountRoleDTO(roleId, accountId));
    }
}
