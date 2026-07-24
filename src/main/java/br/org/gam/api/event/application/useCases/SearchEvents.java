package br.org.gam.api.event.application.useCases;

import br.org.gam.api.event.application.EventMapper;
import br.org.gam.api.event.application.EventRDTO;
import br.org.gam.api.event.application.search.EventSearchFilterConverter;
import br.org.gam.api.event.persistence.EventEntity;
import br.org.gam.api.event.persistence.EventRepository;
import br.org.gam.api.event.persistence.EventSecuritySpecification;
import br.org.gam.api.event.persistence.EventSpecifications;
import br.org.gam.api.security.SecurityUtils;
import br.org.gam.api.shared.specification.SearchDTO;
import java.util.Set;
import java.time.Instant;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class SearchEvents {
    private final EventRepository eventRepo;
    private final EventMapper eventMapper;
    private final SecurityUtils securityUtils;
    private final EventSearchFilterConverter searchFilterConverter;

    public SearchEvents(EventRepository eventRepo, EventMapper eventMapper, SecurityUtils securityUtils,
                        EventSearchFilterConverter searchFilterConverter) {
        this.eventRepo = eventRepo;
        this.eventMapper = eventMapper;
        this.securityUtils = securityUtils;
        this.searchFilterConverter = searchFilterConverter;
    }

    public Page<EventRDTO> search(SearchDTO searchDTO, Pageable pageable) {
        Instant evaluationInstant = Instant.now();
        Set<String> authorities = securityUtils.getLoggedUserAuthorities();
        Specification<EventEntity> securityFilter = EventSecuritySpecification.canGetEvent(authorities);

        Specification<EventEntity> searchFilters = searchFilterConverter.convert(searchDTO, evaluationInstant);

        Sort sort = pageable.getSort();
        if (sort.isUnsorted()) {
            sort = Sort.by(Sort.Order.asc("beginDate"), Sort.Order.asc("id"));
        } else if (sort.getOrderFor("id") == null) {
            sort = sort.and(Sort.by(Sort.Order.asc("id")));
        }
        Pageable effectivePageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        Specification<EventEntity> spec = securityFilter.and(searchFilters);
        if (sort.getOrderFor("status") != null) {
            spec = spec.and(EventSpecifications.orderByEffectiveStatus(sort, evaluationInstant));
            effectivePageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        } else {
            spec = spec.and(EventSpecifications.fetchLocation());
        }

        Page<EventEntity> entitiesPage = eventRepo.findAll(spec, effectivePageable);

        return entitiesPage.map(entity -> eventMapper.entityToRDTO(entity, evaluationInstant));
    }
}
