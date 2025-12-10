package br.org.gam.api.Entities.location.services.getAllLocations;

import br.org.gam.api.Entities.location.services.getLocationById.GetLocationRDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetAllLocations {
    public Page<GetLocationRDTO> get(Pageable pageable);
}
