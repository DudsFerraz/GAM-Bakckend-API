package br.org.gam.api.Entities.presence;

import br.org.gam.api.Entities.event.EventMapper;
import br.org.gam.api.Entities.member.MemberMapper;
import br.org.gam.api.Entities.presence.persistence.PresenceEntity;
import br.org.gam.api.Entities.presence.services.getPresenceById.GetPresenceByIdDTO;
import br.org.gam.api.Entities.presence.services.registerPresence.RegisterPresenceRDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {MemberMapper.class, EventMapper.class})
public interface PresenceMapper {
    Presence fromEntityToDomain(PresenceEntity presenceEntity);
    PresenceEntity fromDomainToEntity(Presence presence);
    RegisterPresenceRDTO fromEntityToRegisterPresenceResponseDTO(PresenceEntity presenceEntity);
    GetPresenceByIdDTO fromEntityToGetPresenceByIdDTO(PresenceEntity presenceEntity);
}
