package br.org.gam.api.Entities.presence.services.getPresence;


import br.org.gam.api.Entities.presence.services.PresenceRDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface GetPresence {
    PresenceRDTO byIds(UUID memberId, UUID eventId);
    Page<PresenceRDTO> allByEvent(UUID eventId, Pageable pageable);
    Page<PresenceRDTO> allByMember(UUID memberId, Pageable pageable);
}
