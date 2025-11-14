package br.org.gam.api.Entities.RBAC.accountRole.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IAccountRoleRepository extends JpaRepository<AccountRoleEntity, UUID> {
}
