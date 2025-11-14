package br.org.gam.api.Entities.presence.services.getPresencesByEvent;

import br.org.gam.api.Entities.presence.IPresenceMapper;
import br.org.gam.api.Entities.presence.persistence.IPresenceRepository;
import br.org.gam.api.Entities.presence.persistence.PresenceEntity;
import br.org.gam.api.Entities.presence.services.getPresenceById.GetPresenceByIdDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetPresencesByEventService implements IGetPresencesByEventService {
    private final IPresenceRepository presenceRepo;
    private final IPresenceMapper presenceMapper;

    public GetPresencesByEventService(IPresenceRepository presenceRepo, IPresenceMapper presenceMapper) {
        this.presenceRepo = presenceRepo;
        this.presenceMapper = presenceMapper;
    }

    @Override
    public Page<GetPresenceByIdDTO> getEventPresences(UUID eventId, Pageable pageable) {

        Page<PresenceEntity> entitiesPage = presenceRepo.findAllByEvent_Id(eventId, pageable);
        return entitiesPage.map(presenceMapper::fromEntityToGetPresenceByIdDTO);
    }
}
