package br.org.gam.api.Entities.location;

import br.org.gam.api.Entities.location.persistence.LocationEntity;
import br.org.gam.api.Entities.location.services.createLocation.CreateLocationRDTO;
import br.org.gam.api.Entities.location.services.LocationRDTO;
import br.org.gam.api.common.auditing.IgnoreFullAuditFields;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    @IgnoreFullAuditFields
    LocationEntity domainToEntity(Location locationDomain);

    Location entityToDomain(LocationEntity locationEntity);

    CreateLocationRDTO entityToCreateLocationRDTO(LocationEntity locationEntity);

    LocationRDTO entityToLocationRDTO(LocationEntity locationEntity);
}
