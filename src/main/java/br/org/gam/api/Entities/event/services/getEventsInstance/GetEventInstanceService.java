package br.org.gam.api.Entities.event.services.getEventsInstance;

import br.org.gam.api.Entities.event.Event;
import br.org.gam.api.Entities.event.exception.EventNotFoundException;
import br.org.gam.api.Entities.event.persistence.EventEntity;
import br.org.gam.api.Entities.event.persistence.IEventRepository;
import br.org.gam.api.Entities.event.IEventMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetEventInstanceService implements IGetEventInstanceService {

    private final IEventRepository eventRepo;
    private final IEventMapper eventMapper;

    public GetEventInstanceService(IEventRepository eventRepo, IEventMapper eventMapper) {
        this.eventRepo = eventRepo;
        this.eventMapper = eventMapper;
    }

    @Override
    public Event getEventDomainById(UUID id) {
        return eventRepo.findById(id)
                .map(eventMapper::fromEntityToDomain)
                .orElseThrow(() -> new EventNotFoundException("Could not find event with id " + id));
    }

    @Override
    public EventEntity getEventEntityById(UUID id) {
        return eventRepo.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Could not find event with id " + id));
    }
}
