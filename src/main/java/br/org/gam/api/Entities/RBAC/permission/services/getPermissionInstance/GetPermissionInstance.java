package br.org.gam.api.Entities.RBAC.permission.services.getPermissionInstance;

import br.org.gam.api.Entities.RBAC.permission.Permission;
import br.org.gam.api.Entities.RBAC.permission.persistence.PermissionEntity;

import java.util.UUID;

public interface GetPermissionInstance {
    Permission domainById(UUID id);
    PermissionEntity entityById(UUID id);
}
