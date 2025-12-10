package br.org.gam.api.Entities.RBAC.rolePermission.services.getRolePermissions;

import br.org.gam.api.Entities.RBAC.permission.PermissionMapper;
import br.org.gam.api.Entities.RBAC.permission.persistence.PermissionEntity;
import br.org.gam.api.Entities.RBAC.permission.services.getPermissionById.GetPermissionByIdRDTO;
import br.org.gam.api.Entities.RBAC.role.Role;
import br.org.gam.api.Entities.RBAC.rolePermission.RolePermission;
import br.org.gam.api.Entities.RBAC.rolePermission.persistence.RolePermissionEntity;
import br.org.gam.api.Entities.RBAC.rolePermission.persistence.RolePermissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SpringGetRolePermissions implements GetRolePermissions {
    private final RolePermissionRepository rolePermissionRepo;
    private final PermissionMapper permissionMapper;

    public SpringGetRolePermissions(RolePermissionRepository rolePermissionRepo, PermissionMapper permissionMapper) {
        this.rolePermissionRepo = rolePermissionRepo;
        this.permissionMapper = permissionMapper;
    }

    @Override
    public GetRolePermissionsRDTO getRolePermissions(UUID roleId) {
        List<RolePermissionEntity> rolePermissionEntities = rolePermissionRepo.findAllByRole_Id(roleId);

        List<PermissionEntity> permissionEntities = rolePermissionEntities
                .stream()
                .map(RolePermissionEntity::getPermission).toList();

        List<GetPermissionByIdRDTO> dtosList = permissionEntities
                .stream()
                .map(permissionMapper::fromEntityToGetPermissionByIdRDTO).toList();

        return new GetRolePermissionsRDTO(dtosList);
    }
}
