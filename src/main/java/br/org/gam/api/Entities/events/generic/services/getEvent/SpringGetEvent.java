package br.org.gam.api.Entities.events.core.services.getEvent;

import br.org.gam.api.Entities.events.core.EventMapper;
import br.org.gam.api.Entities.events.core.exception.EventNotFoundException;
import br.org.gam.api.Entities.events.core.persistence.EventEntity;
import br.org.gam.api.Entities.events.core.security.EventSecurity;
import br.org.gam.api.Entities.events.core.services.EventRDTO;
import br.org.gam.api.Entities.events.core.services.getEventInstance.GetEventInstance;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpringGetEvent implements GetEvent {
    private final GetEventInstance getEventInstance;
    private final EventMapper eventMapper;
    private final EventSecurity eventSecurity;

    public SpringGetEvent(GetEventInstance getEventInstance, EventMapper eventMapper, EventSecurity eventSecurity) {
        this.getEventInstance = getEventInstance;
        this.eventMapper = eventMapper;
        this.eventSecurity = eventSecurity;
    }

    @Override
    public EventRDTO byId(UUID id) {

        EventEntity eventEntity = getEventInstance.entityById(id);

        if(!eventSecurity.canGetEvent(eventEntity)) throw new EventNotFoundException("Could not find event with id " + id);

        return eventMapper.entityToEventRDTO(eventEntity);
    }

}
