package br.org.gam.api.Entities.RBAC.permission;

import br.org.gam.api.Entities.RBAC.permission.persistence.PermissionEntity;
import br.org.gam.api.Entities.RBAC.permission.services.getPermission.GetPermissionRDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionEntity fromDomainToEntity(Permission permission);
    Permission fromEntityToDomain(PermissionEntity permissionEntity);
    GetPermissionRDTO fromEntityToGetPermissionRDTO(PermissionEntity permissionEntity);
}
