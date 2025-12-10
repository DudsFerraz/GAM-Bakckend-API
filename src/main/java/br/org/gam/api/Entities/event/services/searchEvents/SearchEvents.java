package br.org.gam.api.Entities.event.services.searchEvents;

import br.org.gam.api.Entities.event.services.getEvent.GetEventRDTO;
import br.org.gam.api.common.specification.SpecificationFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchEvents {
    Page<GetEventRDTO> search(List<SpecificationFilter> filters, Pageable pageable);
}
