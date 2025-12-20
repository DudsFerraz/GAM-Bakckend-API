package br.org.gam.api.Entities.location.services.getLocation;

import br.org.gam.api.Entities.location.LocationMapper;
import br.org.gam.api.Entities.location.persistence.LocationEntity;
import br.org.gam.api.Entities.location.persistence.LocationRepository;
import br.org.gam.api.Entities.location.services.LocationRDTO;
import br.org.gam.api.Entities.location.services.getLocationInstance.GetLocationInstance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpringGetLocation implements GetLocation {

    private final GetLocationInstance getLocationInstance;
    private final LocationMapper locationMapper;
    private final LocationRepository locationRepo;

    public SpringGetLocation(GetLocationInstance getLocationInstance, LocationMapper locationMapper, LocationRepository locationRepo) {
        this.getLocationInstance = getLocationInstance;
        this.locationMapper = locationMapper;
        this.locationRepo = locationRepo;
    }

    @Override
    public LocationRDTO byId(UUID id) {

        LocationEntity locationEntity = getLocationInstance.entityById(id);
        return locationMapper.entityToLocationRDTO(locationEntity);
    }

    @Override
    public Page<LocationRDTO> all(Pageable pageable) {

        return locationRepo.findAll(pageable)
                .map(locationMapper::entityToLocationRDTO);
    }
}
