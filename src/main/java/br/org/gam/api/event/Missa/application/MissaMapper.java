package br.org.gam.api.event.missa.application;

import br.org.gam.api.event.application.EventMapper;
import br.org.gam.api.event.missa.application.useCases.createMissa.CreateMissaRDTO;
import br.org.gam.api.event.missa.domain.Missa;
import br.org.gam.api.event.missa.persistence.MissaEntity;
import br.org.gam.api.member.application.MemberMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {EventMapper.class, MemberMapper.class})
public interface MissaMapper {

    // =====================================================================================
    // Domain <-> Persistence
    // =====================================================================================

    MissaEntity domainToEntity(Missa domain);

    Missa entityToDomain(MissaEntity entity);

    // =====================================================================================
    // Persistence -> RDTO
    // =====================================================================================

    MissaRDTO entityToRDTO(MissaEntity entity);

    CreateMissaRDTO entityToCreateMissaRDTO(MissaEntity entity);
}
