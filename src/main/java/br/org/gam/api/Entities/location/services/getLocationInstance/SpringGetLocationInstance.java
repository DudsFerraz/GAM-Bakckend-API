package br.org.gam.api.Entities.location.services.getLocationInstance;

import br.org.gam.api.Entities.location.Location;
import br.org.gam.api.Entities.location.exception.LocationNotFoundException;
import br.org.gam.api.Entities.location.persistence.LocationRepository;
import br.org.gam.api.Entities.location.persistence.LocationEntity;
import br.org.gam.api.Entities.location.LocationMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpringGetLocationInstance implements GetLocationInstance {

    private final LocationRepository locationRepo;
    private final LocationMapper locationMapper;

    public SpringGetLocationInstance(LocationRepository locationRepo, LocationMapper locationMapper) {
        this.locationRepo = locationRepo;
        this.locationMapper = locationMapper;
    }

    @Override
    public Location domainById(UUID id) {
        return locationRepo.findById(id)
                .map(locationMapper::entityToDomain)
                .orElseThrow(() -> new LocationNotFoundException("Could not find location with id " + id));
    }

    @Override
    public LocationEntity entityById(UUID id) {
        return locationRepo.findById(id)
                .orElseThrow(() -> new LocationNotFoundException("Could not find location with id " + id));
    }
}
