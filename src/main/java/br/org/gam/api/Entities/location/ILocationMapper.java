package br.org.gam.api.Entities.location;

import br.org.gam.api.Entities.location.persistence.LocationEntity;
import br.org.gam.api.Entities.location.services.createLocation.CreateLocationResponseDTO;
import br.org.gam.api.Entities.location.services.getLocationById.GetLocationByIdDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ILocationMapper {
    LocationEntity fromDomainToEntity(Location account);

    Location fromEntityToDomain(LocationEntity locationEntity);

    CreateLocationResponseDTO fromEntityToCreateLocationResponseDTO(LocationEntity locationEntity);

    GetLocationByIdDTO fromEntityToGetLocationByIdDTO(LocationEntity locationEntity);
}
