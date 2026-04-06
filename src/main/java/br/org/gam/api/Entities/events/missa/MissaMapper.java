package br.org.gam.api.Entities.events.missa;

import br.org.gam.api.Entities.events.generic.EventMapper;
import br.org.gam.api.Entities.events.missa.persistence.MissaEntity;
import br.org.gam.api.Entities.events.missa.services.MissaRDTO;
import br.org.gam.api.Entities.events.missa.services.createMissa.CreateMissaRDTO;
import br.org.gam.api.Entities.member.MemberMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {EventMapper.class, MemberMapper.class})
public interface MissaMapper {
    MissaEntity domainToEntity(Missa domain);
    Missa entityToDomain(MissaEntity entity);
    MissaRDTO entityToRDTO(MissaEntity entity);
    CreateMissaRDTO entityToCreateMissaRDTO(MissaEntity entity);
}
