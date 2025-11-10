package br.org.gam.api.Entities.event.services.getEventById;

import br.org.gam.api.Entities.event.persistence.EventEntity;
import br.org.gam.api.Entities.event.IEventMapper;
import br.org.gam.api.Entities.event.services.getEventsInstance.IGetEventInstanceService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetEventByIdService implements IGetEventByIdService {
    private final IGetEventInstanceService getEventInstanceService;
    private final IEventMapper eventMapper;

    public GetEventByIdService(IGetEventInstanceService getEventInstanceService, IEventMapper eventMapper) {
        this.getEventInstanceService = getEventInstanceService;
        this.eventMapper = eventMapper;
    }

    @Override
    public GetEventByIdDTO getEventById(UUID id) {

        EventEntity eventEntity = getEventInstanceService.getEventEntityById(id);
        return eventMapper.fromEntityToGetEventByIdDTO(eventEntity);
    }
}
