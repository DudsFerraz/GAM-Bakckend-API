package br.org.gam.api.Entities.presence.services.getPresence;

import br.org.gam.api.Entities.event.exception.EventNotFoundException;
import br.org.gam.api.Entities.event.persistence.EventEntity;
import br.org.gam.api.Entities.event.security.EventSecurity;
import br.org.gam.api.Entities.event.services.getEventInstance.GetEventInstance;
import br.org.gam.api.Entities.presence.PresenceMapper;
import br.org.gam.api.Entities.presence.exception.PresenceNotFoundException;
import br.org.gam.api.Entities.presence.persistence.PresenceEntity;
import br.org.gam.api.Entities.presence.persistence.PresenceRepository;
import br.org.gam.api.Entities.presence.persistence.PresenceSpecifications;
import br.org.gam.api.Entities.presence.services.PresenceRDTO;
import br.org.gam.api.Entities.presence.services.getPresenceInstance.GetPresenceInstance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpringGetPresence implements GetPresence {
    private final PresenceMapper presenceMapper;
    private final PresenceRepository presenceRepo;
    private final EventSecurity eventSecurity;
    private final GetEventInstance getEventInstance;

    public SpringGetPresence(PresenceMapper presenceMapper, PresenceRepository presenceRepo, EventSecurity eventSecurity, GetEventInstance getEventInstance) {
        this.presenceMapper = presenceMapper;
        this.presenceRepo = presenceRepo;
        this.eventSecurity = eventSecurity;
        this.getEventInstance = getEventInstance;
    }

    @Override
    public PresenceRDTO byIds(UUID memberId, UUID eventId) {
        Specification<PresenceEntity> spec = PresenceSpecifications.fetchEvent()
                .and(PresenceSpecifications.fetchMember())
                .and(PresenceSpecifications.filterByEventId(eventId))
                .and(PresenceSpecifications.filterByMemberId(memberId));

        return presenceRepo.findOne(spec)
                .map(presenceMapper::entityToPresenceRDTO)
                .orElseThrow(() -> new PresenceNotFoundException(
                        String.format("member with id: %s has no presence registered in event with id: %s", memberId, eventId)
                        ));
    }

    @Override
    public Page<PresenceRDTO> allByEvent(UUID eventId, Pageable pageable) {
        EventEntity eventEntity = getEventInstance.entityById(eventId);
        if(!eventSecurity.canGetEvent(eventEntity)) throw new EventNotFoundException("Could not find event with id " + eventId);

        Specification<PresenceEntity> spec = PresenceSpecifications.fetchEvent()
                .and(PresenceSpecifications.fetchMember())
                .and(PresenceSpecifications.filterByEventId(eventId));

        Page<PresenceEntity> entitiesPage = presenceRepo.findAll(spec, pageable);
        return entitiesPage.map(presenceMapper::entityToPresenceRDTO);
    }

    @Override
    public Page<PresenceRDTO> allByMember(UUID memberId, Pageable pageable) {

        Specification<PresenceEntity> spec = PresenceSpecifications.fetchEvent()
                .and(PresenceSpecifications.fetchMember())
                .and(PresenceSpecifications.filterByMemberId(memberId));

        Page<PresenceEntity> entitiesPage = presenceRepo.findAll(spec, pageable);
        return entitiesPage.map(presenceMapper::entityToPresenceRDTO);
    }
}
