package br.org.gam.api.Entities.event.services.getEvent;

import br.org.gam.api.Entities.event.services.EventRDTO;

import java.util.UUID;

public interface GetEvent {
    EventRDTO byId(UUID id);
}
