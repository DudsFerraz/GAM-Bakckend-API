package br.org.gam.api.Entities.event.services.getEventsInstance;

import br.org.gam.api.Entities.event.Event;
import br.org.gam.api.Entities.event.persistence.EventEntity;

import java.util.UUID;

public interface IGetEventInstanceService {
    public Event getEventDomainById(UUID id);
    public EventEntity getEventEntityById(UUID id);
}
