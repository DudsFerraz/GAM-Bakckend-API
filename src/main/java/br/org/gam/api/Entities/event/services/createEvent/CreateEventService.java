package br.org.gam.api.Entities.event.services.createEvent;

import br.org.gam.api.Entities.event.Event;
import br.org.gam.api.Entities.event.persistence.EventEntity;
import br.org.gam.api.Entities.event.persistence.IEventRepository;
import br.org.gam.api.Entities.event.IEventMapper;
import br.org.gam.api.Entities.location.ILocationMapper;
import br.org.gam.api.Entities.location.Location;
import br.org.gam.api.Entities.location.persistence.LocationEntity;
import br.org.gam.api.Entities.location.services.getLocationInstance.service.IGetLocationInstanceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service
public class CreateEventService implements ICreateEventService {

    private final IEventRepository eventRepository;
    private final IGetLocationInstanceService getLocationInstanceService;
    private final IEventMapper eventMapper;
    private final ILocationMapper locationMapper;
    public CreateEventService(IEventRepository eventRepository, IGetLocationInstanceService getLocationInstanceService, IEventMapper eventMapper, ILocationMapper locationMapper) {
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

    public CreateEventResponseDTO createEvent(CreateEventDTO dto) {
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
