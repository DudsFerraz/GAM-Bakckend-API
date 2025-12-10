package br.org.gam.api.Entities.RBAC.accountRole.persistence;

import br.org.gam.api.Entities.RBAC.role.Role;
import br.org.gam.api.Entities.RBAC.role.persistence.RoleEntity;
import br.org.gam.api.Entities.account.persistence.AccountEntity;
import br.org.gam.api.common.persistence.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRoleRepository extends BaseRepository<AccountRoleEntity, UUID> {
    List<AccountRoleEntity> findAllByAccount_Id(UUID accountId);
    boolean existsByAccount_IdAndRole_Id(UUID accountId, UUID roleId);
    Optional<AccountRoleEntity> findByAccount_IdAndRole_Id(UUID accountId, UUID roleId);
}
