package br.org.gam.api.Entities.events.core.services.getEventInstance;

import br.org.gam.api.Entities.events.core.Event;
import br.org.gam.api.Entities.events.core.persistence.EventEntity;

import java.util.UUID;

public interface GetEventInstance {
    public Event domainById(UUID id);
    public EventEntity entityById(UUID id);
}
