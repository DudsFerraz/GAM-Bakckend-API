package br.org.gam.api.Entities.event.services.getEventInstance;

import br.org.gam.api.Entities.event.Event;
import br.org.gam.api.Entities.event.persistence.EventEntity;

import java.util.UUID;

public interface GetEventInstance {
    public Event domainById(UUID id);
    public EventEntity entityById(UUID id);
}
