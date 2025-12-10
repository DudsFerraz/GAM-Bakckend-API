package br.org.gam.api.Entities.RBAC.accountRole.services.getAccountRoles;

import br.org.gam.api.Entities.RBAC.accountRole.persistence.AccountRoleEntity;
import br.org.gam.api.Entities.RBAC.accountRole.persistence.AccountRoleRepository;
import br.org.gam.api.Entities.RBAC.role.RoleMapper;
import br.org.gam.api.Entities.RBAC.role.persistence.RoleEntity;
import br.org.gam.api.Entities.RBAC.role.services.getRoleById.GetRoleByIdRDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SpringGetAccountRoles implements GetAccountRoles {
    private final AccountRoleRepository accountRoleRepo;
    private final RoleMapper roleMapper;

    public SpringGetAccountRoles(AccountRoleRepository accountRoleRepo, RoleMapper roleMapper) {
        this.accountRoleRepo = accountRoleRepo;
        this.roleMapper = roleMapper;
    }

    @Override
    public GetAccountRolesRDTO get(UUID accountId) {
        List<AccountRoleEntity> accountRolesEntities = accountRoleRepo.findAllByAccount_Id(accountId);

        List<RoleEntity> rolesEntities = accountRolesEntities
                .stream()
                .map(AccountRoleEntity::getRole).toList();

        List<GetRoleByIdRDTO> dtosList = rolesEntities
                .stream()
                .map(roleMapper::fromEntityToGetRoleByIdDTO).toList();

        return new GetAccountRolesRDTO(dtosList);
    }
}
