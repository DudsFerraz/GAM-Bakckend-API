package br.org.gam.api.Entities.location.services.getAllLocations;

import br.org.gam.api.Entities.location.LocationMapper;
import br.org.gam.api.Entities.location.persistence.LocationRepository;
import br.org.gam.api.Entities.location.services.getLocationById.GetLocationRDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SpringGetAllLocations implements GetAllLocations {

    private final LocationRepository locationRepo;
    private final LocationMapper locationMapper;

    public SpringGetAllLocations(LocationRepository locationRepo, LocationMapper locationMapper) {
        this.locationRepo = locationRepo;
        this.locationMapper = locationMapper;
    }

    @Override
    public Page<GetLocationRDTO> get(Pageable pageable) {

        return locationRepo.findAll(pageable)
                .map(locationMapper::fromEntityToGetLocationRDTO);
    }
}
