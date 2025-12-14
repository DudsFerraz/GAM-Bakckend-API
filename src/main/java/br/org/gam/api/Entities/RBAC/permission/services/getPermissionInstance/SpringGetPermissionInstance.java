package br.org.gam.api.Entities.RBAC.permission.services.getPermissionInstance;

import br.org.gam.api.Entities.RBAC.permission.Permission;
import br.org.gam.api.Entities.RBAC.permission.PermissionMapper;
import br.org.gam.api.Entities.RBAC.permission.exception.PermissionNotFoundException;
import br.org.gam.api.Entities.RBAC.permission.persistence.PermissionEntity;
import br.org.gam.api.Entities.RBAC.permission.persistence.PermissionRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpringGetPermissionInstance implements GetPermissionInstance {
    private final PermissionRepository permissionRepo;
    private final PermissionMapper permissionMapper;

    public SpringGetPermissionInstance(PermissionRepository permissionRepo, PermissionMapper permissionMapper) {
        this.permissionRepo = permissionRepo;
        this.permissionMapper = permissionMapper;
    }

    @Override
    public Permission domainById(UUID id) {
        return permissionRepo.findById(id)
                .map(permissionMapper::fromEntityToDomain)
                .orElseThrow(() -> new PermissionNotFoundException("Could not find permission with id" + id));
    }

    @Override
    public PermissionEntity entityById(UUID id) {
        return permissionRepo.findById(id)
                .orElseThrow(() -> new PermissionNotFoundException("Could not find permission with id" + id));
    }
}
