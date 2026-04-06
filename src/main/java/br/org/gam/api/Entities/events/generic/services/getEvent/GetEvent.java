package br.org.gam.api.Entities.events.generic.services.getEvent;

import br.org.gam.api.Entities.events.generic.services.EventRDTO;

import java.util.UUID;

public interface GetEvent {
    EventRDTO byId(UUID id);
}
