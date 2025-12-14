package br.org.gam.api.Entities.RBAC.accountRole.services.dropAccountRole;

import br.org.gam.api.Entities.RBAC.accountRole.persistence.AccountRoleEntity;
import br.org.gam.api.Entities.RBAC.accountRole.persistence.AccountRoleRepository;
import br.org.gam.api.Entities.RBAC.accountRole.services.AccountRoleDTO;
import br.org.gam.api.Entities.RBAC.accountRole.services.getAccountRoleInstance.GetAccountRoleInstance;
import br.org.gam.api.Entities.RBAC.role.services.getRoleInstance.GetRoleInstance;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpringDropAccountRole implements DropAccountRole {
    private final GetAccountRoleInstance getAccountRoleInstance;
    private final AccountRoleRepository accountRoleRepo;
    private final GetRoleInstance getRoleInstance;

    public SpringDropAccountRole(GetAccountRoleInstance getAccountRoleInstance, AccountRoleRepository accountRoleRepo, GetRoleInstance getRoleInstance) {
        this.getAccountRoleInstance = getAccountRoleInstance;
        this.accountRoleRepo = accountRoleRepo;
        this.getRoleInstance = getRoleInstance;
    }

    @Transactional
    @Override
    public void byDTO(AccountRoleDTO dto) {
        AccountRoleEntity accountRoleEntity = getAccountRoleInstance.entityByDTO(dto);

        accountRoleRepo.delete(accountRoleEntity);
    }

    @Transactional
    @Override
    public void byRoleName(String roleName, UUID accountId) {
        UUID roleId = getRoleInstance.entityByName(roleName).getId();

        byDTO(new AccountRoleDTO(roleId, accountId));
    }
}
