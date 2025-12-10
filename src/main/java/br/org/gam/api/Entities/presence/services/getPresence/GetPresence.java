package br.org.gam.api.Entities.presence.services.getPresence;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface GetPresence {
    GetPresenceRDTO byIds(UUID memberId, UUID eventId);
    Page<GetPresenceRDTO> allByEvent(UUID eventId, Pageable pageable);
    Page<GetPresenceRDTO> allByMember(UUID memberId, Pageable pageable);
}
