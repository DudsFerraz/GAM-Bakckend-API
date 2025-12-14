package br.org.gam.api.Entities.RBAC.permission.services.getPermission;

import br.org.gam.api.Entities.RBAC.permission.PermissionMapper;
import br.org.gam.api.Entities.RBAC.permission.persistence.PermissionEntity;
import br.org.gam.api.Entities.RBAC.permission.services.getPermissionInstance.GetPermissionInstance;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpringGetPermission implements GetPermission {
    private final GetPermissionInstance getPermissionInstance;
    private final PermissionMapper permissionMapper;

    public SpringGetPermission(GetPermissionInstance getPermissionInstance, PermissionMapper permissionMapper) {
        this.getPermissionInstance = getPermissionInstance;
        this.permissionMapper = permissionMapper;
    }

    @Override
    public GetPermissionRDTO byId(UUID id) {

        PermissionEntity permissionEntity = getPermissionInstance.entityById(id);
        return permissionMapper.fromEntityToGetPermissionRDTO(permissionEntity);
    }
}
