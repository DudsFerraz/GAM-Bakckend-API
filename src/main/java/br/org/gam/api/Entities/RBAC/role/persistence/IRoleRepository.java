package br.org.gam.api.Entities.RBAC.role.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IRoleRepository extends JpaRepository<RoleEntity, UUID> {
}
