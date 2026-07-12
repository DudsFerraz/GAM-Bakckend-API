package br.org.gam.api.rbac.application;

import br.org.gam.api.rbac.accountRole.persistence.AccountRoleEntity;
import br.org.gam.api.rbac.accountRole.persistence.AccountRoleRepository;
import br.org.gam.api.rbac.permission.persistence.PermissionEntity;
import br.org.gam.api.rbac.role.domain.SystemRole;
import br.org.gam.api.rbac.role.persistence.RoleEntity;
import br.org.gam.api.rbac.rolePermission.persistence.RolePermissionEntity;
import br.org.gam.api.security.application.AccountDetails;
import br.org.gam.api.shared.exception.ForbiddenOperationException;
import br.org.gam.api.shared.exception.NotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class RbacSafetyPolicy {
    private final AccountRoleRepository accountRoleRepo;

    public RbacSafetyPolicy(AccountRoleRepository accountRoleRepo) {
        this.accountRoleRepo = accountRoleRepo;
    }

    public void assertCanAssignRoleThroughAdmin(RoleEntity role) {
        if (isSudo(role)) {
            throw ForbiddenOperationException.reason("SUDO role assignment is developer-controlled.");
        }
    }

    public void assertCanRemoveRoleThroughAdmin(AccountRoleEntity accountRole) {
        if (isSudo(accountRole.getRole())) {
            throw ForbiddenOperationException.reason("SUDO role removal is developer-controlled.");
        }

        assertCanRemoveOnlyCoordCapabilityFromSelf(accountRole);
    }

    public void assertCanRemoveSudoThroughInternalService(AccountRoleEntity accountRole) {
        if (!isSudo(accountRole.getRole())) {
            throw ForbiddenOperationException.reason("Only SUDO role removal is allowed through this internal service.");
        }

        List<AccountRoleEntity> activeSudoRoles =
                accountRoleRepo.lockActiveAccountRolesByRoleName(SystemRole.SUDO.getCode());
        if (activeSudoRoles == null) {
            activeSudoRoles = List.of();
        }
        UUID targetAccountId = accountRole.getAccount().getId();
        UUID sudoRoleId = accountRole.getRole().getId();
        boolean targetIsActiveSudo = activeSudoRoles.stream()
                .map(AccountRoleEntity::getAccount)
                .filter(Objects::nonNull)
                .anyMatch(account -> targetAccountId.equals(account.getId()));

        if (!targetIsActiveSudo) {
            throw NotFoundException.resource(
                    "AccountRole",
                    "%s:%s".formatted(targetAccountId, sudoRoleId)
            );
        }

        if (activeSudoRoles.size() <= 1) {
            throw ForbiddenOperationException.reason("Cannot remove the last active SUDO account.");
        }
    }

    public void assertRoleCanBeManaged(RoleEntity role) {
        if (role.isSystemManaged()) {
            throw ForbiddenOperationException.reason("System-managed roles cannot be edited, deleted, or disabled.");
        }
    }

    public void assertPermissionCanBeManaged(PermissionEntity permission) {
        if (permission.isSystemManaged()) {
            throw ForbiddenOperationException.reason("System-managed permissions cannot be edited, deleted, or disabled.");
        }
    }

    public void assertRolePermissionCanBeManaged(RolePermissionEntity rolePermission) {
        RoleEntity role = rolePermission.getRole();
        if (role != null && role.isSystemManaged()) {
            throw ForbiddenOperationException.reason("System-managed role-permission links cannot be edited.");
        }
    }

    private boolean isSudo(RoleEntity role) {
        return role != null && SystemRole.SUDO.getCode().equals(role.getName());
    }

    private void assertCanRemoveOnlyCoordCapabilityFromSelf(AccountRoleEntity accountRole) {
        if (!isCoord(accountRole.getRole())) {
            return;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof AccountDetails accountDetails)) {
            return;
        }

        UUID targetAccountId = accountRole.getAccount() == null ? null : accountRole.getAccount().getId();
        if (!Objects.equals(accountDetails.getId(), targetAccountId)) {
            return;
        }

        if (isSudoAccount(accountDetails.getId())) {
            return;
        }

        List<AccountRoleEntity> activeCoordRoles =
                accountRoleRepo.lockActiveAccountRolesByRoleName(SystemRole.COORD.getCode());
        if (activeCoordRoles == null || activeCoordRoles.size() <= 1) {
            throw ForbiddenOperationException.reason("Cannot remove the last active COORD account.");
        }
    }

    private boolean isSudoAccount(UUID accountId) {
        List<AccountRoleEntity> activeSudoRoles =
                accountRoleRepo.lockActiveAccountRolesByRoleName(SystemRole.SUDO.getCode());
        return activeSudoRoles != null
                && activeSudoRoles.stream()
                .map(AccountRoleEntity::getAccount)
                .filter(Objects::nonNull)
                .anyMatch(account -> accountId.equals(account.getId()));
    }

    private boolean isCoord(RoleEntity role) {
        return role != null && SystemRole.COORD.getCode().equals(role.getName());
    }
}
