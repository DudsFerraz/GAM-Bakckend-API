package br.org.gam.api.rbac.accountRole.persistence;

import br.org.gam.api.shared.persistence.BaseRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;

public interface AccountRoleRepository extends BaseRepository<AccountRoleEntity, UUID> {
    @Query("""
            select accountRole
            from AccountRoleEntity accountRole
            join accountRole.account account
            join accountRole.role role
            where account.id = :accountId
              and role.id = :roleId
              and accountRole.deletedAt is null
              and account.deletedAt is null
              and role.deletedAt is null
            """)
    Optional<AccountRoleEntity> findByAccount_IdAndRole_Id(@Param("accountId") UUID accountId,
                                                            @Param("roleId") UUID roleId);

    @Query("""
            select accountRole
            from AccountRoleEntity accountRole
            join accountRole.account account
            join accountRole.role role
            where account.id = :accountId
              and accountRole.deletedAt is null
              and account.deletedAt is null
              and role.deletedAt is null
            """)
    List<AccountRoleEntity> findAllByAccount_Id(@Param("accountId") UUID accountId);

    boolean existsByAccount_IdAndRole_Id(UUID accountId, UUID roleId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            select accountRole
            from AccountRoleEntity accountRole
            join accountRole.account account
            join accountRole.role role
            where role.name = :roleName
              and accountRole.deletedAt is null
              and account.deletedAt is null
              and role.deletedAt is null
            """)
    List<AccountRoleEntity> lockActiveAccountRolesByRoleName(@Param("roleName") String roleName);
}
