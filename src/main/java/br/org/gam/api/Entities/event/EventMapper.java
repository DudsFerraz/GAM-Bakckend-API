package br.org.gam.api.Entities.event;

import br.org.gam.api.Entities.event.persistence.EventEntity;
import br.org.gam.api.Entities.event.services.createEvent.CreateEventRDTO;
import br.org.gam.api.Entities.event.services.getEvent.GetEventRDTO;
import br.org.gam.api.Entities.location.LocationMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {LocationMapper.class})
public interface EventMapper {
    EventEntity fromDomainToEntity(Event event);
    Event fromEntityToDomain(EventEntity eventEntity);
    CreateEventRDTO fromEntityToCreateEventRDTO(EventEntity eventEntity);
    GetEventRDTO fromEntityToGetEventRDTO(EventEntity eventEntity);
}
