package br.org.gam.api.Entities.events.generic;

import br.org.gam.api.Entities.RBAC.permission.PermissionMapper;
import br.org.gam.api.Entities.events.generic.persistence.EventEntity;
import br.org.gam.api.Entities.events.generic.services.createEvent.CreateEventRDTO;
import br.org.gam.api.Entities.events.generic.services.EventRDTO;
import br.org.gam.api.Entities.location.LocationMapper;
import br.org.gam.api.common.auditing.IgnoreFullAuditFields;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {LocationMapper.class, PermissionMapper.class})
public interface EventMapper {
    @IgnoreFullAuditFields
    EventEntity domainToEntity(Event event);
    Event entityToDomain(EventEntity eventEntity);
    CreateEventRDTO entityToCreateEventRDTO(EventEntity eventEntity);
    EventRDTO entityToEventRDTO(EventEntity eventEntity);
}
