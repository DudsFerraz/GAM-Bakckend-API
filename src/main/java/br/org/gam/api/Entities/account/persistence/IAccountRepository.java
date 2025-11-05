package br.org.gam.api.Entities.account.persistence;

import br.org.gam.api.Entities.account.common.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface IAccountRepository extends JpaRepository<AccountEntity, UUID>,
                                            JpaSpecificationExecutor<AccountEntity> {

    boolean existsByEmail(Email email);
}
