package br.org.gam.api.Entities.location.services.getLocationById;

import br.org.gam.api.Entities.location.persistence.LocationEntity;
import br.org.gam.api.Entities.location.ILocationMapper;
import br.org.gam.api.Entities.location.services.getLocationInstance.service.IGetLocationInstanceService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetLocationByIdService implements IGetLocationByIdService {

    private final IGetLocationInstanceService getLocationInstanceService;
    private final ILocationMapper locationMapper;

    public GetLocationByIdService(IGetLocationInstanceService getLocationInstanceService, ILocationMapper locationMapper) {
        this.getLocationInstanceService = getLocationInstanceService;
        this.locationMapper = locationMapper;
    }

    @Override
    public GetLocationByIdDTO getLocationById(UUID id) {

        LocationEntity locationEntity = getLocationInstanceService.getLocationEntityById(id);
        return locationMapper.fromEntityToGetLocationByIdDTO(locationEntity);
    }
}
