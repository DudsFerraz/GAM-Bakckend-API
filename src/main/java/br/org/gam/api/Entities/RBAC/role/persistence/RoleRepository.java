package br.org.gam.api.Entities.RBAC.role.persistence;

import br.org.gam.api.common.persistence.BaseRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends BaseRepository<RoleEntity, UUID> {
    Optional<RoleEntity> findByName(String name);
}
