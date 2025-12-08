package br.org.gam.api.Entities.location.services.createLocation;

import br.org.gam.api.Entities.location.Location;
import br.org.gam.api.Entities.location.persistence.LocationRepository;
import br.org.gam.api.Entities.location.persistence.LocationEntity;
import br.org.gam.api.Entities.location.LocationMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class SpringCreateLocation implements CreateLocation {

    private final LocationRepository locationRepo;
    private final LocationMapper locationMapper;

    public SpringCreateLocation(LocationRepository locationRepo, LocationMapper locationMapper) {
        this.locationRepo = locationRepo;
        this.locationMapper = locationMapper;
    }

    @Transactional
    @Override
    public CreateLocationRDTO createLocation(CreateLocationDTO dto) {

        Location newLocation = Location.create(dto.name(), dto.street(), dto.city(), dto.state(), dto.postalCode(),
                                                dto.countryCode(), dto.latitude(), dto.longitude());
        LocationEntity locationEntity = locationMapper.fromDomainToEntity(newLocation);
        LocationEntity savedLocationEntity = locationRepo.save(locationEntity);

        return locationMapper.fromEntityToCreateLocationResponseDTO(savedLocationEntity);
    }
}
