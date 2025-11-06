package br.org.gam.api.Entities.member.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface IMemberRepository extends JpaRepository<MemberEntity, UUID>,
                                           JpaSpecificationExecutor<MemberEntity> {

    boolean existsByAccountId(UUID accountId);
}
