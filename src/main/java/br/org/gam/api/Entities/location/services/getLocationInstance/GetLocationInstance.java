package br.org.gam.api.Entities.location.services.getLocationInstance;

import br.org.gam.api.Entities.location.Location;
import br.org.gam.api.Entities.location.persistence.LocationEntity;

import java.util.UUID;

public interface GetLocationInstance {
    public Location getLocationDomainById(UUID id);
    public LocationEntity getLocationEntityById(UUID id);
}
