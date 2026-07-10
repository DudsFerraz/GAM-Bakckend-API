package br.org.gam.api.account.persistence;

import br.org.gam.api.shared.domain.GamEmail;
import br.org.gam.api.shared.persistence.BaseRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccountRepository extends BaseRepository<AccountEntity, UUID>,
                                            JpaSpecificationExecutor<AccountEntity> {

    boolean existsByEmail(GamEmail email);
    Optional<AccountEntity> findByEmail(GamEmail email);
}
