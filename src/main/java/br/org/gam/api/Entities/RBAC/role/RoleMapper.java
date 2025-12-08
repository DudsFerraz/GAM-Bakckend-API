package br.org.gam.api.Entities.RBAC.role;

import br.org.gam.api.Entities.RBAC.role.persistence.RoleEntity;
import br.org.gam.api.Entities.RBAC.role.services.getRoleById.GetRoleByIdDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleEntity fromDomainToEntity(Role role);
    Role fromEntityToDomain(RoleEntity roleEntity);
    GetRoleByIdDTO fromEntityToGetRoleByIdDTO(RoleEntity roleEntity);
}
