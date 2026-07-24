package br.org.gam.api.rbac.rolePermission.persistence;

import br.org.gam.api.shared.persistence.BaseRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RolePermissionRepository extends BaseRepository<RolePermissionEntity, UUID>,
                                                  JpaSpecificationExecutor<RolePermissionEntity> {
}
