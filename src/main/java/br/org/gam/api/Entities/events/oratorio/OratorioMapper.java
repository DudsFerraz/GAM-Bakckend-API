package br.org.gam.api.Entities.events.oratorio;

import br.org.gam.api.Entities.events.generic.EventMapper;
import br.org.gam.api.Entities.events.oratorio.persistence.OratorioEntity;
import br.org.gam.api.Entities.events.oratorio.services.OratorioRDTO;
import br.org.gam.api.Entities.events.oratorio.services.createOratorio.CreateOratorioRDTO;
import br.org.gam.api.Entities.member.MemberMapper;
import br.org.gam.api.Entities.oratoriano.OratorianoMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {EventMapper.class, MemberMapper.class, OratorianoMapper.class})
public interface OratorioMapper {
    OratorioEntity domainToEntity(Oratorio domain);
    Oratorio entityToDomain(OratorioEntity entity);
    OratorioRDTO entityToRDTO(OratorioEntity entity);
    CreateOratorioRDTO entityToCreateOratorioRDTO(OratorioEntity entity);
}
