package br.org.gam.api.Entities.location.services.getAllLocations;

import br.org.gam.api.Entities.location.persistence.ILocationRepository;
import br.org.gam.api.Entities.location.ILocationMapper;
import br.org.gam.api.Entities.location.services.getLocationById.GetLocationByIdDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GetAllLocationsService implements IGetAllLocationsService {

    private final ILocationRepository locationRepo;
    private final ILocationMapper locationMapper;

    public GetAllLocationsService(ILocationRepository locationRepo, ILocationMapper locationMapper) {
        this.locationRepo = locationRepo;
        this.locationMapper = locationMapper;
    }

    @Override
    public Page<GetLocationByIdDTO> getAllLocations(Pageable pageable) {

        return locationRepo.findAll(pageable)
                .map(locationMapper::fromEntityToGetLocationByIdDTO);
    }
}
