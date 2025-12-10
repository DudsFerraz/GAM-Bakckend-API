package br.org.gam.api.Entities.member.persistence;

import br.org.gam.api.common.persistence.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface MemberRepository extends BaseRepository<MemberEntity, UUID>,
                                           JpaSpecificationExecutor<MemberEntity> {

    boolean existsByAccountId(UUID accountId);
}
