package br.org.gam.api.Entities.oratoriano;

import br.org.gam.api.Entities.oratoriano.persistence.OratorianoEntity;
import br.org.gam.api.Entities.oratoriano.services.OratorianoRDTO;
import br.org.gam.api.common.Name;
import br.org.gam.api.common.auditing.IgnoreFullAuditFields;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface OratorianoMapper {
    @IgnoreFullAuditFields
    OratorianoEntity domainToEntity(Oratoriano oratorianoDomain);

    Oratoriano entityToDomain(OratorianoEntity oratorianoEntity);

    @Named("nameToString")
    default String nameToString(Name name) {
        if (name == null) return null;
        return name.toString();
    }

    @Mapping(target = "name", source = "oratorianoEntity.name", qualifiedByName = "nameToString")
    OratorianoRDTO entityToRDTO(OratorianoEntity oratorianoEntity);
}
