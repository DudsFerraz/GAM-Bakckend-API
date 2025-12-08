package br.org.gam.api.Entities.RBAC.permission.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PermissionRepository extends JpaRepository<PermissionEntity, UUID> {
}
