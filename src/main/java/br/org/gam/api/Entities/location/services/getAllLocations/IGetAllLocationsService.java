package br.org.gam.api.Entities.location.services.getAllLocations;

import br.org.gam.api.Entities.location.services.getLocationById.GetLocationByIdDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IGetAllLocationsService {
    public Page<GetLocationByIdDTO> getAllLocations(Pageable pageable);
}
