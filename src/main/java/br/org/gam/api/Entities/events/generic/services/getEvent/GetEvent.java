package br.org.gam.api.Entities.events.core.services.getEvent;

import br.org.gam.api.Entities.events.core.services.EventRDTO;

import java.util.UUID;

public interface GetEvent {
    EventRDTO byId(UUID id);
}
