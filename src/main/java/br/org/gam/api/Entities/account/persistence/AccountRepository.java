package br.org.gam.api.Entities.account.persistence;

import br.org.gam.api.Entities.account.MyEmail;
import br.org.gam.api.common.persistence.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends BaseRepository<AccountEntity, UUID>,
                                            JpaSpecificationExecutor<AccountEntity> {

    boolean existsByEmail(MyEmail email);
    Optional<AccountEntity> findByEmail(MyEmail email);
}
