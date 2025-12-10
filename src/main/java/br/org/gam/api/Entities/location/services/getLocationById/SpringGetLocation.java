package br.org.gam.api.Entities.location.services.getLocationById;

import br.org.gam.api.Entities.location.LocationMapper;
import br.org.gam.api.Entities.location.persistence.LocationEntity;
import br.org.gam.api.Entities.location.services.getLocationInstance.GetLocationInstance;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpringGetLocation implements GetLocation {

    private final GetLocationInstance getLocationInstance;
    private final LocationMapper locationMapper;

    public SpringGetLocation(GetLocationInstance getLocationInstance, LocationMapper locationMapper) {
        this.getLocationInstance = getLocationInstance;
        this.locationMapper = locationMapper;
    }

    @Override
    public GetLocationRDTO byId(UUID id) {

        LocationEntity locationEntity = getLocationInstance.entityById(id);
        return locationMapper.fromEntityToGetLocationRDTO(locationEntity);
    }
}
