package br.org.gam.api.Entities.RBAC.permission.persistence;

import br.org.gam.api.common.persistence.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PermissionRepository extends BaseRepository<PermissionEntity, UUID> {
}
