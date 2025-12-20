package br.org.gam.api.Entities.RBAC.rolePermission.persistence;

import br.org.gam.api.common.persistence.BaseRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface RolePermissionRepository extends BaseRepository<RolePermissionEntity, UUID>,
                                                  JpaSpecificationExecutor<RolePermissionEntity> {
}
