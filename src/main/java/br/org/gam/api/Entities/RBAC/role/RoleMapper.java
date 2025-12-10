package br.org.gam.api.Entities.RBAC.role;

import br.org.gam.api.Entities.RBAC.role.persistence.RoleEntity;
import br.org.gam.api.Entities.RBAC.role.services.getRoleById.GetRoleByIdRDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleEntity fromDomainToEntity(Role role);
    Role fromEntityToDomain(RoleEntity roleEntity);
    GetRoleByIdRDTO fromEntityToGetRoleByIdDTO(RoleEntity roleEntity);
}
