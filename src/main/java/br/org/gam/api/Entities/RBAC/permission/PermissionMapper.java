package br.org.gam.api.Entities.RBAC.permission;

import br.org.gam.api.Entities.RBAC.permission.persistence.PermissionEntity;
import br.org.gam.api.Entities.RBAC.permission.services.PermissionRDTO;
import br.org.gam.api.common.auditing.IgnoreFullAuditFields;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    @IgnoreFullAuditFields
    PermissionEntity domainToEntity(Permission permissionDomain);
    Permission entityToDomain(PermissionEntity permissionEntity);
    PermissionRDTO entityToPermissionRDTO(PermissionEntity permissionEntity);
}
