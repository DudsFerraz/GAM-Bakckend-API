package br.org.gam.api.Entities.RBAC.rolePermission.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RolePermissionRepository extends JpaRepository<RolePermissionEntity, UUID> {
    List<RolePermissionEntity> findAllByRole_Id(UUID roleId);
}
