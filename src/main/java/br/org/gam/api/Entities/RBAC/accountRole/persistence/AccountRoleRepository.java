package br.org.gam.api.Entities.RBAC.accountRole.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AccountRoleRepository extends JpaRepository<AccountRoleEntity, UUID> {

    List<AccountRoleEntity> findAllByAccount_Id(UUID accountId);
}
