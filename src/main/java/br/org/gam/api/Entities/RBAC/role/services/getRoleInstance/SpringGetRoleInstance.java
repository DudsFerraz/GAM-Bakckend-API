package br.org.gam.api.Entities.RBAC.role.services.getRoleInstance;

import br.org.gam.api.Entities.RBAC.role.RoleMapper;
import br.org.gam.api.Entities.RBAC.role.Role;
import br.org.gam.api.Entities.RBAC.role.exception.RoleNotFoundException;
import br.org.gam.api.Entities.RBAC.role.persistence.RoleRepository;
import br.org.gam.api.Entities.RBAC.role.persistence.RoleEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpringGetRoleInstance implements GetRoleInstance {
    private final RoleRepository roleRepo;
    private final RoleMapper roleMapper;

    public SpringGetRoleInstance(RoleRepository roleRepo, RoleMapper roleMapper) {
        this.roleRepo = roleRepo;
        this.roleMapper = roleMapper;
    }

    @Override
    public Role domainById(UUID id) {
        return roleRepo.findById(id)
                .map(roleMapper::entityToDomain)
                .orElseThrow(() -> new RoleNotFoundException("Could not find role with id" + id));
    }

    @Override
    public RoleEntity entityById(UUID id) {
        return roleRepo.findById(id)
                .orElseThrow(() -> new RoleNotFoundException("Could not find role with id" + id));
    }

    @Override
    public RoleEntity entityByName(String name) {
        return roleRepo.findByName(name)
                .orElseThrow(() -> new RoleNotFoundException("Could not find role with name" + name));
    }
}
