package br.org.gam.api.Entities.location.services.getLocation;

import br.org.gam.api.Entities.location.services.LocationRDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface GetLocation {
    LocationRDTO byId(UUID id);
    Page<LocationRDTO> all(Pageable pageable);
}
