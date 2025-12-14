package br.org.gam.api.Entities.RBAC.rolePermission.persistence;

import br.org.gam.api.common.persistence.BaseRepository;

import java.util.List;
import java.util.UUID;

public interface RolePermissionRepository extends BaseRepository<RolePermissionEntity, UUID> {
    List<RolePermissionEntity> findAllByRole_Id(UUID roleId);
}
