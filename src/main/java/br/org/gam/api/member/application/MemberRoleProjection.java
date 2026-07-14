package br.org.gam.api.member.application;

import br.org.gam.api.account.persistence.AccountEntity;
import br.org.gam.api.account.application.AccountEntityLoader;
import br.org.gam.api.rbac.accountRole.persistence.AccountRoleEntity;
import br.org.gam.api.rbac.accountRole.persistence.AccountRoleRepository;
import br.org.gam.api.rbac.role.application.RoleEntityLoader;
import br.org.gam.api.rbac.role.domain.SystemRole;
import br.org.gam.api.rbac.role.persistence.RoleEntity;
import br.org.gam.api.shared.persistence.UUIDGenerator;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class MemberRoleProjection {
    private final AccountRoleRepository accountRoleRepo;
    private final RoleEntityLoader roleEntityLoader;
    private final AccountEntityLoader accountEntityLoader;

    public MemberRoleProjection(AccountRoleRepository accountRoleRepo, RoleEntityLoader roleEntityLoader,
                                AccountEntityLoader accountEntityLoader) {
        this.accountRoleRepo = accountRoleRepo;
        this.roleEntityLoader = roleEntityLoader;
        this.accountEntityLoader = accountEntityLoader;
    }

    public RoleChange synchronizeActive(UUID accountId) {
        return synchronize(accountEntityLoader.requiredById(accountId), SystemRole.MEMBER, SystemRole.VISITOR);
    }

    public RoleChange synchronizeInactive(UUID accountId) {
        return synchronize(accountEntityLoader.requiredById(accountId), SystemRole.VISITOR, SystemRole.MEMBER);
    }

    private RoleChange synchronize(AccountEntity account, SystemRole roleToAdd, SystemRole roleToRemove) {
        RoleEntity addedRole = roleEntityLoader.requiredByName(roleToAdd.getCode());
        RoleEntity removedRole = roleEntityLoader.requiredByName(roleToRemove.getCode());

        if (!accountRoleRepo.existsByAccount_IdAndRole_Id(account.getId(), addedRole.getId())) {
            AccountRoleEntity assignment = new AccountRoleEntity();
            assignment.setId(UUIDGenerator.generateUUIDV7());
            assignment.setAccount(account);
            assignment.setRole(addedRole);
            accountRoleRepo.save(assignment);
        }

        accountRoleRepo.findByAccount_IdAndRole_Id(account.getId(), removedRole.getId())
                .ifPresent(accountRoleRepo::delete);

        return new RoleChange(addedRole.getId(), removedRole.getId());
    }

    public record RoleChange(UUID roleAddedId, UUID roleRemovedId) {
    }
}
