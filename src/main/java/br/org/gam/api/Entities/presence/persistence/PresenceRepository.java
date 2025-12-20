package br.org.gam.api.Entities.presence.persistence;

import br.org.gam.api.common.persistence.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface PresenceRepository extends BaseRepository<PresenceEntity, UUID>,
                                             JpaSpecificationExecutor<PresenceEntity> {
    Optional<PresenceEntity> findByMember_IdAndEvent_Id(UUID memberId, UUID eventId);
    boolean existsByMember_IdAndEvent_Id(UUID memberId, UUID eventId);
}
