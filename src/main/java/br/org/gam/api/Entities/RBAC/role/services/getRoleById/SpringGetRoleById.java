package br.org.gam.api.Entities.RBAC.role.services.getRoleById;

import br.org.gam.api.Entities.RBAC.role.RoleMapper;
import br.org.gam.api.Entities.RBAC.role.persistence.RoleEntity;
import br.org.gam.api.Entities.RBAC.role.services.getRoleInstance.GetRoleInstance;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpringGetRoleById implements GetRoleById {
    private final GetRoleInstance getRoleInstance;
    private final RoleMapper roleMapper;

    public SpringGetRoleById(GetRoleInstance getRoleInstance, RoleMapper roleMapper) {
        this.getRoleInstance = getRoleInstance;
        this.roleMapper = roleMapper;
    }

    @Override
    public GetRoleByIdRDTO getRoleById(UUID id) {

        RoleEntity roleEntity = getRoleInstance.getRoleEntityById(id);
        return roleMapper.fromEntityToGetRoleByIdDTO(roleEntity);
    }
}
