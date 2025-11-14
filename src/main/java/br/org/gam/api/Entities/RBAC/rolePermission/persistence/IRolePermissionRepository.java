package br.org.gam.api.Entities.RBAC.rolePermission.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IRolePermissionRepository extends JpaRepository<RolePermissionEntity, UUID> {
}
