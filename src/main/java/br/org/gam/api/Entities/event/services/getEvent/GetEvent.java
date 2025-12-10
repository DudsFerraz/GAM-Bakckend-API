package br.org.gam.api.Entities.event.services.getEvent;

import java.util.UUID;

public interface GetEvent {
    GetEventRDTO byId(UUID id);
}
