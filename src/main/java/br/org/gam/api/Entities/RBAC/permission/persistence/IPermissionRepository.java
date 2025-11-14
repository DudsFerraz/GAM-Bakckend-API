package br.org.gam.api.Entities.RBAC.permission.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface IPermissionRepository extends JpaRepository<PermissionEntity, UUID> {
}
