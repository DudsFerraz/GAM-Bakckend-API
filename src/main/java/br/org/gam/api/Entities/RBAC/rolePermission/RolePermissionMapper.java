package br.org.gam.api.Entities.RBAC.rolePermission;

import br.org.gam.api.Entities.RBAC.permission.PermissionMapper;
import br.org.gam.api.Entities.RBAC.rolePermission.persistence.RolePermissionEntity;
import br.org.gam.api.common.auditing.IgnoreJunctionAuditFields;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PermissionMapper.class})
public interface RolePermissionMapper {
    @IgnoreJunctionAuditFields
    RolePermissionEntity domainToEntity(RolePermission rolePermissionDomain);
    RolePermission entityToDomain(RolePermissionEntity rolePermissionEntity);
}
