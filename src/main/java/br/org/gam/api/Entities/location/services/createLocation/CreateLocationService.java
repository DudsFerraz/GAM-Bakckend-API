package br.org.gam.api.Entities.location.services.createLocation;

import br.org.gam.api.Entities.location.Location;
import br.org.gam.api.Entities.location.persistence.ILocationRepository;
import br.org.gam.api.Entities.location.persistence.LocationEntity;
import br.org.gam.api.Entities.location.ILocationMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CreateLocationService implements ICreateLocationService {

    private final ILocationRepository locationRepo;
    private final ILocationMapper locationMapper;

    public CreateLocationService(ILocationRepository locationRepo, ILocationMapper locationMapper) {
        this.locationRepo = locationRepo;
        this.locationMapper = locationMapper;
    }

    @Transactional
    @Override
    public CreateLocationResponseDTO createLocation(CreateLocationDTO dto) {

        Location newLocation = Location.create(dto.name(), dto.street(), dto.city(), dto.state(), dto.postalCode(),
                                                dto.countryCode(), dto.latitude(), dto.longitude());
        LocationEntity locationEntity = locationMapper.fromDomainToEntity(newLocation);
        LocationEntity savedLocationEntity = locationRepo.save(locationEntity);

        return locationMapper.fromEntityToCreateLocationResponseDTO(savedLocationEntity);
    }
}
