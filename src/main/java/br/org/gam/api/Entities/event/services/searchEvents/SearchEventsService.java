package br.org.gam.api.Entities.event.services.searchEvents;

import br.org.gam.api.Entities.event.persistence.EventEntity;
import br.org.gam.api.Entities.event.persistence.IEventRepository;
import br.org.gam.api.Entities.event.IEventMapper;
import br.org.gam.api.Entities.event.services.getEventById.GetEventByIdDTO;
import br.org.gam.api.common.specification.SpecificationBuilder;
import br.org.gam.api.common.specification.SpecificationFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchEventsService implements ISearchEventsService {
    private final IEventRepository eventRepo;
    private final IEventMapper eventMapper;

    public SearchEventsService(IEventRepository eventRepo, IEventMapper eventMapper) {
        this.eventRepo = eventRepo;
        this.eventMapper = eventMapper;
    }

    @Override
    public Page<GetEventByIdDTO> searchEvents(List<SpecificationFilter> filters, Pageable pageable) {
        Specification<EventEntity> spec = SpecificationBuilder.build(filters);

        Page<EventEntity> entitiesPage = eventRepo.findAll(spec, pageable);

        return entitiesPage.map(eventMapper::fromEntityToGetEventByIdDTO);
    }
}
