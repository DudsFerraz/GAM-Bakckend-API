package br.org.gam.api.Entities.events.generic.services.getEventInstance;

import br.org.gam.api.Entities.events.generic.Event;
import br.org.gam.api.Entities.events.generic.exception.EventNotFoundException;
import br.org.gam.api.Entities.events.generic.persistence.EventEntity;
import br.org.gam.api.Entities.events.generic.persistence.EventRepository;
import br.org.gam.api.Entities.events.generic.EventMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpringGetEventInstance implements GetEventInstance {

    private final EventRepository eventRepo;
    private final EventMapper eventMapper;

    public SpringGetEventInstance(EventRepository eventRepo, EventMapper eventMapper) {
        this.eventRepo = eventRepo;
        this.eventMapper = eventMapper;
    }

    @Override
    public Event domainById(UUID id) {
        return eventRepo.findById(id)
                .map(eventMapper::entityToDomain)
                .orElseThrow(() -> new EventNotFoundException("Could not find event with id " + id));
    }

    @Override
    public EventEntity entityById(UUID id) {
        return eventRepo.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Could not find event with id " + id));
    }
}
