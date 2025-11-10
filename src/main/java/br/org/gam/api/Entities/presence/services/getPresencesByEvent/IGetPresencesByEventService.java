package br.org.gam.api.Entities.presence.services.getPresencesByEvent;

import br.org.gam.api.Entities.presence.services.getPresenceById.GetPresenceByIdDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IGetPresencesByEventService {
    Page<GetPresenceByIdDTO> getEventPresences(UUID eventId, Pageable pageable);
}
