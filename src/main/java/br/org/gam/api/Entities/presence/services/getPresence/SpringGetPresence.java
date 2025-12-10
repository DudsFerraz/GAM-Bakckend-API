package br.org.gam.api.Entities.presence.services.getPresence;

import br.org.gam.api.Entities.presence.PresenceMapper;
import br.org.gam.api.Entities.presence.persistence.PresenceEntity;
import br.org.gam.api.Entities.presence.persistence.PresenceRepository;
import br.org.gam.api.Entities.presence.services.getPresenceInstance.GetPresenceInstance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpringGetPresence implements GetPresence {
    private final GetPresenceInstance getPresenceInstance;
    private final PresenceMapper presenceMapper;
    private final PresenceRepository presenceRepo;

    public SpringGetPresence(GetPresenceInstance getPresenceInstance, PresenceMapper presenceMapper, PresenceRepository presenceRepo) {
        this.getPresenceInstance = getPresenceInstance;
        this.presenceMapper = presenceMapper;
        this.presenceRepo = presenceRepo;
    }

    @Override
    public GetPresenceRDTO byIds(UUID memberId, UUID eventId) {

        PresenceEntity presenceEntity = getPresenceInstance.entityByIds(memberId, eventId);
        return presenceMapper.fromEntityToGetPresenceRDTO(presenceEntity);
    }

    @Override
    public Page<GetPresenceRDTO> allByEvent(UUID eventId, Pageable pageable) {

        Page<PresenceEntity> entitiesPage = presenceRepo.findAllByEvent_Id(eventId, pageable);
        return entitiesPage.map(presenceMapper::fromEntityToGetPresenceRDTO);
    }

    @Override
    public Page<GetPresenceRDTO> allByMember(UUID memberId, Pageable pageable) {

        Page<PresenceEntity> entitiesPage = presenceRepo.findAllByMember_Id(memberId, pageable);
        return entitiesPage.map(presenceMapper::fromEntityToGetPresenceRDTO);
    }
}
