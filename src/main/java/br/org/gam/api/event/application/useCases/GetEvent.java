package br.org.gam.api.event.application.useCases;

import br.org.gam.api.event.application.EventMapper;
import br.org.gam.api.event.application.EventRDTO;
import br.org.gam.api.event.application.EventSecurity;
import br.org.gam.api.event.application.EventEntityLoader;
import br.org.gam.api.event.persistence.EventEntity;
import br.org.gam.api.shared.exception.NotFoundException;
import java.util.UUID;
import java.time.Instant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetEvent {
    private final EventEntityLoader getEventInstance;
    private final EventMapper eventMapper;
    private final EventSecurity eventSecurity;

    public GetEvent(EventEntityLoader getEventInstance, EventMapper eventMapper, EventSecurity eventSecurity) {
        this.getEventInstance = getEventInstance;
        this.eventMapper = eventMapper;
        this.eventSecurity = eventSecurity;
    }
    @Transactional(readOnly = true)
    public EventRDTO byId(UUID id) {
        Instant evaluationInstant = Instant.now();
        EventEntity eventEntity = getEventInstance.requiredById(id);

        if(!eventSecurity.canGetEvent(eventEntity)) throw NotFoundException.resource("Event", id);

        return eventMapper.entityToRDTO(eventEntity, evaluationInstant);
    }

}
