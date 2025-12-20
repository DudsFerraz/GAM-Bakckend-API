package br.org.gam.api.Entities.RBAC.role.services.getRolePermissions;

import br.org.gam.api.Entities.RBAC.permission.PermissionMapper;
import br.org.gam.api.Entities.RBAC.permission.persistence.PermissionEntity;
import br.org.gam.api.Entities.RBAC.permission.services.PermissionRDTO;
import br.org.gam.api.Entities.RBAC.rolePermission.persistence.RolePermissionEntity;
import br.org.gam.api.Entities.RBAC.rolePermission.persistence.RolePermissionRepository;
import br.org.gam.api.Entities.RBAC.rolePermission.persistence.RolePermissionSpecifications;
import org.springframework.data.jpa.domain.Specification;
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
    public GetRolePermissionsRDTO allById(UUID roleId) {
        Specification<RolePermissionEntity> spec = RolePermissionSpecifications.filterByRoleId(roleId)
                .and(RolePermissionSpecifications.fetchPermission())
                .and(RolePermissionSpecifications.fetchRole());

        List<RolePermissionEntity> rolePermissionEntities = rolePermissionRepo.findAll(spec);

        List<PermissionEntity> permissionEntities = rolePermissionEntities
                .stream()
                .map(RolePermissionEntity::getPermission).toList();

        List<PermissionRDTO> dtosList = permissionEntities
                .stream()
                .map(permissionMapper::entityToPermissionRDTO).toList();

        return new GetRolePermissionsRDTO(dtosList);
    }
}
