package br.org.gam.api.Entities.location.services.getLocationInstance.service;

import br.org.gam.api.Entities.location.Location;
import br.org.gam.api.Entities.location.exception.LocationNotFoundException;
import br.org.gam.api.Entities.location.persistence.ILocationRepository;
import br.org.gam.api.Entities.location.persistence.LocationEntity;
import br.org.gam.api.Entities.location.ILocationMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetLocationInstanceService implements IGetLocationInstanceService {

    private final ILocationRepository locationRepo;
    private final ILocationMapper locationMapper;

    public GetLocationInstanceService(ILocationRepository locationRepo, ILocationMapper locationMapper) {
        this.locationRepo = locationRepo;
        this.locationMapper = locationMapper;
    }

    @Override
    public Location getLocationDomainById(UUID id) {
        return locationRepo.findById(id)
                .map(locationMapper::fromEntityToDomain)
                .orElseThrow(() -> new LocationNotFoundException("Could not find location with id " + id));
    }

    @Override
    public LocationEntity getLocationEntityById(UUID id) {
        return locationRepo.findById(id)
                .orElseThrow(() -> new LocationNotFoundException("Could not find location with id " + id));
    }
}
