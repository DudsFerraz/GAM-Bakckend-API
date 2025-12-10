package br.org.gam.api.Entities.RBAC.permission.services.getPermissionById;

import br.org.gam.api.Entities.RBAC.permission.PermissionMapper;
import br.org.gam.api.Entities.RBAC.permission.persistence.PermissionEntity;
import br.org.gam.api.Entities.RBAC.permission.services.getPermissionInstance.GetPermissionInstance;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpringGetPermissionById implements GetPermissionById {
    private final GetPermissionInstance getPermissionInstance;
    private final PermissionMapper permissionMapper;

    public SpringGetPermissionById(GetPermissionInstance getPermissionInstance, PermissionMapper permissionMapper) {
        this.getPermissionInstance = getPermissionInstance;
        this.permissionMapper = permissionMapper;
    }

    @Override
    public GetPermissionByIdRDTO GetPermissionById(UUID id) {

        PermissionEntity permissionEntity = getPermissionInstance.getPermissionEntityById(id);
        return permissionMapper.fromEntityToGetPermissionByIdRDTO(permissionEntity);
    }
}
