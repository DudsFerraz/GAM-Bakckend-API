package br.org.gam.api.rbac.role.application.useCases;

import br.org.gam.api.rbac.role.application.RoleMapper;
import br.org.gam.api.rbac.role.application.RoleRDTO;
import br.org.gam.api.rbac.role.application.RoleEntityLoader;
import br.org.gam.api.rbac.role.persistence.RoleEntity;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class GetRole {
    private final RoleEntityLoader getRoleInstance;
    private final RoleMapper roleMapper;

    public GetRole(RoleEntityLoader getRoleInstance, RoleMapper roleMapper) {
        this.getRoleInstance = getRoleInstance;
        this.roleMapper = roleMapper;
    }
    public RoleRDTO byId(UUID id) {

        RoleEntity roleEntity = getRoleInstance.requiredById(id);
        return roleMapper.entityToRDTO(roleEntity);
    }
}
