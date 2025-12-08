package br.org.gam.api.Entities.location.services.getLocationById;

import br.org.gam.api.Entities.location.LocationMapper;
import br.org.gam.api.Entities.location.persistence.LocationEntity;
import br.org.gam.api.Entities.location.services.getLocationInstance.GetLocationInstance;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpringGetLocationById implements GetLocationById {

    private final GetLocationInstance getLocationInstanceService;
    private final LocationMapper locationMapper;

    public SpringGetLocationById(GetLocationInstance getLocationInstanceService, LocationMapper locationMapper) {
        this.getLocationInstanceService = getLocationInstanceService;
        this.locationMapper = locationMapper;
    }

    @Override
    public GetLocationByIdDTO getLocationById(UUID id) {

        LocationEntity locationEntity = getLocationInstanceService.getLocationEntityById(id);
        return locationMapper.fromEntityToGetLocationByIdDTO(locationEntity);
    }
}
