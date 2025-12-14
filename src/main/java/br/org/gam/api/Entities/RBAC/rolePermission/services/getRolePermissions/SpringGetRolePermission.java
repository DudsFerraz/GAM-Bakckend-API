package br.org.gam.api.Entities.RBAC.rolePermission.services.getRolePermissions;

import br.org.gam.api.Entities.RBAC.permission.PermissionMapper;
import br.org.gam.api.Entities.RBAC.permission.persistence.PermissionEntity;
import br.org.gam.api.Entities.RBAC.permission.services.getPermission.GetPermissionRDTO;
import br.org.gam.api.Entities.RBAC.rolePermission.persistence.RolePermissionEntity;
import br.org.gam.api.Entities.RBAC.rolePermission.persistence.RolePermissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SpringGetRolePermission implements GetRolePermission {
    private final RolePermissionRepository rolePermissionRepo;
    private final PermissionMapper permissionMapper;

    public SpringGetRolePermission(RolePermissionRepository rolePermissionRepo, PermissionMapper permissionMapper) {
        this.rolePermissionRepo = rolePermissionRepo;
        this.permissionMapper = permissionMapper;
    }

    @Override
    public GetRolePermissionsRDTO allById(UUID roleId) {
        List<RolePermissionEntity> rolePermissionEntities = rolePermissionRepo.findAllByRole_Id(roleId);

        List<PermissionEntity> permissionEntities = rolePermissionEntities
                .stream()
                .map(RolePermissionEntity::getPermission).toList();

        List<GetPermissionRDTO> dtosList = permissionEntities
                .stream()
                .map(permissionMapper::fromEntityToGetPermissionRDTO).toList();

        return new GetRolePermissionsRDTO(dtosList);
    }
}
