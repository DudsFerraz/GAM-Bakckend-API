package br.org.gam.api.Entities.events.core.services.createEvent;

import br.org.gam.api.Entities.RBAC.permission.Permission;
import br.org.gam.api.Entities.RBAC.permission.services.getPermissionInstance.GetPermissionInstance;
import br.org.gam.api.Entities.events.core.Event;
import br.org.gam.api.Entities.events.core.EventMapper;
import br.org.gam.api.Entities.events.core.persistence.EventEntity;
import br.org.gam.api.Entities.events.core.persistence.EventRepository;
import br.org.gam.api.Entities.location.Location;
import br.org.gam.api.Entities.location.services.getLocationInstance.GetLocationInstance;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service
public class SpringCreateEvent implements CreateEvent {

    private final EventRepository eventRepository;
    private final GetLocationInstance getLocationInstanceService;
    private final EventMapper eventMapper;
    private final GetPermissionInstance getPermissionInstance;
    public SpringCreateEvent(EventRepository eventRepository, GetLocationInstance getLocationInstanceService, EventMapper eventMapper, GetPermissionInstance getPermissionInstance) {
        this.eventRepository = eventRepository;
        this.getLocationInstanceService = getLocationInstanceService;
        this.eventMapper = eventMapper;
        this.getPermissionInstance = getPermissionInstance;
    }

    @Transactional
    @Override
    public CreateEventRDTO create(CreateEventDTO dto) {

        Location eventLocation = getLocationInstanceService.domainById(dto.locationId());
        Permission requiredPermission = getPermissionInstance.domainById(dto.requiredPermissionId());

        Event newEvent = Event.register(dto.title(), dto.description(), eventLocation, requiredPermission,
                                      dto.beginDate(), dto.endDate());

        EventEntity newEventEntity = eventMapper.domainToEntity(newEvent);
        EventEntity savedEventEntity = eventRepository.save(newEventEntity);

        return eventMapper.entityToCreateEventRDTO(savedEventEntity);
    }

}
