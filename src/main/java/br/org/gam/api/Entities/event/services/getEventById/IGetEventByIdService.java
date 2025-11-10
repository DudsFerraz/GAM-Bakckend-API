package br.org.gam.api.Entities.event.services.getEventById;

import java.util.UUID;

public interface IGetEventByIdService {
    GetEventByIdDTO getEventById(UUID id);
}
