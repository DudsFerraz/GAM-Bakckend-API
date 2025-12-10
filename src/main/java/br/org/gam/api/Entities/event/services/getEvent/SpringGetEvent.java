package br.org.gam.api.Entities.event.services.getEvent;

import br.org.gam.api.Entities.event.EventMapper;
import br.org.gam.api.Entities.event.persistence.EventEntity;
import br.org.gam.api.Entities.event.services.getEventInstance.GetEventInstance;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpringGetEvent implements GetEvent {
    private final GetEventInstance getEventInstance;
    private final EventMapper eventMapper;

    public SpringGetEvent(GetEventInstance getEventInstance, EventMapper eventMapper) {
        this.getEventInstance = getEventInstance;
        this.eventMapper = eventMapper;
    }

    @Override
    public GetEventRDTO byId(UUID id) {

        EventEntity eventEntity = getEventInstance.entityById(id);
        return eventMapper.fromEntityToGetEventRDTO(eventEntity);
    }
}
