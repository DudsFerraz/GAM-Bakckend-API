package br.org.gam.api.Entities.event.services.getEventById;

import br.org.gam.api.Entities.event.EventMapper;
import br.org.gam.api.Entities.event.persistence.EventEntity;
import br.org.gam.api.Entities.event.services.getEventsInstance.GetEventInstance;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpringGetEventById implements GetEventById {
    private final GetEventInstance getEventInstanceService;
    private final EventMapper eventMapper;

    public SpringGetEventById(GetEventInstance getEventInstanceService, EventMapper eventMapper) {
        this.getEventInstanceService = getEventInstanceService;
        this.eventMapper = eventMapper;
    }

    @Override
    public GetEventByIdDTO getEventById(UUID id) {

        EventEntity eventEntity = getEventInstanceService.getEventEntityById(id);
        return eventMapper.fromEntityToGetEventByIdDTO(eventEntity);
    }
}
