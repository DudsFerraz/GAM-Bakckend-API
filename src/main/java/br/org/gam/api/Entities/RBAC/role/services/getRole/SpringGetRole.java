package br.org.gam.api.Entities.RBAC.role.services.getRole;

import br.org.gam.api.Entities.RBAC.role.RoleMapper;
import br.org.gam.api.Entities.RBAC.role.persistence.RoleEntity;
import br.org.gam.api.Entities.RBAC.role.services.getRoleInstance.GetRoleInstance;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpringGetRole implements GetRole {
    private final GetRoleInstance getRoleInstance;
    private final RoleMapper roleMapper;

    public SpringGetRole(GetRoleInstance getRoleInstance, RoleMapper roleMapper) {
        this.getRoleInstance = getRoleInstance;
        this.roleMapper = roleMapper;
    }

    @Override
    public GetRoleRDTO byId(UUID id) {

        RoleEntity roleEntity = getRoleInstance.entityById(id);
        return roleMapper.fromEntityToGetRoleRDTO(roleEntity);
    }
}
