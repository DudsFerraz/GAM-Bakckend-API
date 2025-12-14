package br.org.gam.api.Entities.RBAC.role.services.getRoleInstance;

import br.org.gam.api.Entities.RBAC.role.Role;
import br.org.gam.api.Entities.RBAC.role.persistence.RoleEntity;

import java.util.UUID;

public interface GetRoleInstance {
    Role domainById(UUID id);
    RoleEntity entityById(UUID id);
    RoleEntity entityByName(String name);
}
