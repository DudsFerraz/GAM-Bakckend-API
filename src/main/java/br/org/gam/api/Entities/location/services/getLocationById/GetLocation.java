package br.org.gam.api.Entities.location.services.getLocationById;

import java.util.UUID;

public interface GetLocation {
    public GetLocationRDTO byId(UUID id);
}
