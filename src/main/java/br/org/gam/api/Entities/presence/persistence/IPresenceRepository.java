package br.org.gam.api.Entities.presence.persistence;

import br.org.gam.api.Entities.presence.PresenceId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface IPresenceRepository extends JpaRepository<PresenceEntity, PresenceId>,
                                             JpaSpecificationExecutor<PresenceEntity> {
    Page<PresenceEntity> findAllByMember_Id(UUID memberId, Pageable pageable);
    Page<PresenceEntity> findAllByEvent_Id(UUID eventId, Pageable pageable);
}
