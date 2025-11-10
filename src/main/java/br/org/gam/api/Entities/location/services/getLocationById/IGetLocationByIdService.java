package br.org.gam.api.Entities.location.services.getLocationById;

import java.util.UUID;

public interface IGetLocationByIdService {
    public GetLocationByIdDTO getLocationById(UUID id);
}
