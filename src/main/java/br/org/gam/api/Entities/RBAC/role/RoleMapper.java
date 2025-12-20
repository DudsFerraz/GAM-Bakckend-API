package br.org.gam.api.Entities.RBAC.role;

import br.org.gam.api.Entities.RBAC.role.persistence.RoleEntity;
import br.org.gam.api.Entities.RBAC.role.services.RoleRDTO;
import br.org.gam.api.common.auditing.IgnoreFullAuditFields;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @IgnoreFullAuditFields
    RoleEntity domainToEntity(Role role);
    Role entityToDomain(RoleEntity roleEntity);
    RoleRDTO entityToRoleRDTO(RoleEntity roleEntity);
}
