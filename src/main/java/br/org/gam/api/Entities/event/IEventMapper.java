package br.org.gam.api.Entities.event;

import br.org.gam.api.Entities.event.persistence.EventEntity;
import br.org.gam.api.Entities.event.services.createEvent.CreateEventResponseDTO;
import br.org.gam.api.Entities.event.services.getEventById.GetEventByIdDTO;
import br.org.gam.api.Entities.location.ILocationMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ILocationMapper.class})
public interface IEventMapper {
    EventEntity fromDomainToEntity(Event event);
    Event fromEntityToDomain(EventEntity eventEntity);
    CreateEventResponseDTO fromEntityToCreateEventResponseDTO(EventEntity eventEntity);
    GetEventByIdDTO fromEntityToGetEventByIdDTO(EventEntity eventEntity);
}
