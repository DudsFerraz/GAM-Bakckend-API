package br.org.gam.api.Entities.RBAC.role.services.getRoleById;

import br.org.gam.api.Entities.RBAC.role.RoleMapper;
import br.org.gam.api.Entities.RBAC.role.persistence.RoleEntity;
import br.org.gam.api.Entities.RBAC.role.services.getRoleInstance.GetRoleInstance;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpringGetRoleById implements GetRoleById {
    private final GetRoleInstance getRoleInstanceService;
    private final RoleMapper roleMapper;

    public SpringGetRoleById(GetRoleInstance getRoleInstanceService, RoleMapper roleMapper) {
        this.getRoleInstanceService = getRoleInstanceService;
        this.roleMapper = roleMapper;
    }

    @Override
    public GetRoleByIdDTO getRoleById(UUID id) {

        RoleEntity roleEntity = getRoleInstanceService.getRoleEntityById(id);
        return roleMapper.fromEntityToGetRoleByIdDTO(roleEntity);
    }
}
