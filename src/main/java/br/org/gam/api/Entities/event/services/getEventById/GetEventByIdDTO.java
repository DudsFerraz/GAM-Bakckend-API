package br.org.gam.api.Entities.event.services.getEventById;

import br.org.gam.api.Entities.location.services.getLocationById.GetLocationByIdDTO;

import java.time.Instant;
import java.util.UUID;

public record GetEventByIdDTO(
        UUID id,
        String title,
        String description,
        GetLocationByIdDTO location,
        Instant beginDate,
        Instant endDate
) {
}
