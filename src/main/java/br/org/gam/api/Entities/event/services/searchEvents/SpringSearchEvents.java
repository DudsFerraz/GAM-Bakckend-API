package br.org.gam.api.Entities.event.services.searchEvents;

import br.org.gam.api.Entities.event.EventMapper;
import br.org.gam.api.Entities.event.persistence.EventEntity;
import br.org.gam.api.Entities.event.persistence.EventRepository;
import br.org.gam.api.Entities.event.services.getEvent.GetEventRDTO;
import br.org.gam.api.common.security.SecurityUtils;
import br.org.gam.api.Entities.event.security.EventSecuritySpecification;
import br.org.gam.api.common.specification.SpecificationBuilder;
import br.org.gam.api.common.specification.SpecificationFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SpringSearchEvents implements SearchEvents {
    private final EventRepository eventRepo;
    private final EventMapper eventMapper;
    private final SecurityUtils securityUtils;

    public SpringSearchEvents(EventRepository eventRepo, EventMapper eventMapper, SecurityUtils securityUtils) {
        this.eventRepo = eventRepo;
        this.eventMapper = eventMapper;
        this.securityUtils = securityUtils;
    }

    @Override
    public Page<GetEventRDTO> search(List<SpecificationFilter> filters, Pageable pageable) {
        Set<String> authorities = securityUtils.getLoggedUserAuthorities();
        Specification<EventEntity> securityFilter = EventSecuritySpecification.canGetEvent(authorities);

        Specification<EventEntity> searchFilters = SpecificationBuilder.build(filters);

        Specification<EventEntity> spec = securityFilter.and(searchFilters);

        Page<EventEntity> entitiesPage = eventRepo.findAll(spec, pageable);

        return entitiesPage.map(eventMapper::fromEntityToGetEventRDTO);
    }
}
