package br.org.gam.api.Entities.event.services.getEventById;

import java.util.UUID;

public interface GetEventById {
    GetEventByIdDTO getEventById(UUID id);
}
