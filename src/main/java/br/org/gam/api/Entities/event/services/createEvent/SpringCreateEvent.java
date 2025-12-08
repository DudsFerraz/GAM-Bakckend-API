package br.org.gam.api.Entities.event.services.createEvent;

import br.org.gam.api.Entities.event.Event;
import br.org.gam.api.Entities.event.EventMapper;
import br.org.gam.api.Entities.event.persistence.EventEntity;
import br.org.gam.api.Entities.event.persistence.EventRepository;
import br.org.gam.api.Entities.location.LocationMapper;
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
    private final LocationMapper locationMapper;
    public SpringCreateEvent(EventRepository eventRepository, GetLocationInstance getLocationInstanceService, EventMapper eventMapper, LocationMapper locationMapper) {
        this.eventRepository = eventRepository;
        this.getLocationInstanceService = getLocationInstanceService;
        this.eventMapper = eventMapper;
        this.locationMapper = locationMapper;
    }

//    @Override
//    public CreateEventResponseDTO createEvent(CreateEventDTO dto) {
//        Location eventLocation = null;
//        if(dto.locationId() != null){
//            eventLocation = getLocationInstanceService.getLocationDomainById(dto.locationId());
//        }
//
//        Event newEvent = Event.create(dto.title(), dto.description(), eventLocation, dto.requiredPermissionLevel(),
//                                      dto.beginDate(), dto.endDate());
//
//        EventEntity newEventEntity = eventMapper.fromDomainToEntity(newEvent);
//
//        if (dto.locationId() != null) {
//            LocationEntity managedLocation = getLocationInstanceService.getLocationEntityById(dto.locationId());
//            newEventEntity.setLocation(managedLocation);
//        }
//
//        EventEntity savedEventEntity = eventRepository.save(newEventEntity);
//        return eventMapper.fromEntityToCreateEventResponseDTO(savedEventEntity);
//    }

    @Override

    public CreateEventRDTO createEvent(CreateEventDTO dto) {
        Location eventLocation = null;
        if(dto.locationId() != null) {
            eventLocation = getLocationInstanceService.getLocationDomainById(dto.locationId());
        }

        Event newEvent = Event.create(dto.title(), dto.description(), eventLocation,
                                      dto.beginDate(), dto.endDate());

        EventEntity newEventEntity = eventMapper.fromDomainToEntity(newEvent);
        EventEntity savedEventEntity = eventRepository.save(newEventEntity);

        return eventMapper.fromEntityToCreateEventResponseDTO(savedEventEntity);
    }

}
