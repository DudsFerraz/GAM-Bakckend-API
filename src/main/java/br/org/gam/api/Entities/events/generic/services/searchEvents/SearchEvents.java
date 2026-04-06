package br.org.gam.api.Entities.events.core.services.searchEvents;

import br.org.gam.api.Entities.events.core.services.EventRDTO;
import br.org.gam.api.common.specification.SpecificationFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchEvents {
    Page<EventRDTO> search(List<SpecificationFilter> filters, Pageable pageable);
}
