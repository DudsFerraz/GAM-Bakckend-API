package br.org.gam.api.Entities.events.generic.services.getEventInstance;

import br.org.gam.api.Entities.events.generic.Event;
import br.org.gam.api.Entities.events.generic.persistence.EventEntity;

import java.util.UUID;

public interface GetEventInstance {
    public Event domainById(UUID id);
    public EventEntity entityById(UUID id);
}
