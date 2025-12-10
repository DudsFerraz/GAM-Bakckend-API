package br.org.gam.api.Entities.location;

import br.org.gam.api.Entities.location.persistence.LocationEntity;
import br.org.gam.api.Entities.location.services.createLocation.CreateLocationRDTO;
import br.org.gam.api.Entities.location.services.getLocationById.GetLocationRDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    LocationEntity fromDomainToEntity(Location account);

    Location fromEntityToDomain(LocationEntity locationEntity);

    CreateLocationRDTO fromEntityToCreateLocationRDTO(LocationEntity locationEntity);

    GetLocationRDTO fromEntityToGetLocationRDTO(LocationEntity locationEntity);
}
