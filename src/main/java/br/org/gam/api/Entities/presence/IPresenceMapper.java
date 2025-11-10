package br.org.gam.api.Entities.presence;

import br.org.gam.api.Entities.event.IEventMapper;
import br.org.gam.api.Entities.member.IMemberMapper;
import br.org.gam.api.Entities.presence.persistence.PresenceEntity;
import br.org.gam.api.Entities.presence.services.getPresenceById.GetPresenceByIdDTO;
import br.org.gam.api.Entities.presence.services.registerPresence.RegisterPresenceResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {IMemberMapper.class, IEventMapper.class})
public interface IPresenceMapper {
    Presence fromEntityToDomain(PresenceEntity presenceEntity);
    PresenceEntity fromDomainToEntity(Presence presence);
    RegisterPresenceResponseDTO fromEntityToRegisterPresenceResponseDTO(PresenceEntity presenceEntity);
    GetPresenceByIdDTO fromEntityToGetPresenceByIdDTO(PresenceEntity presenceEntity);
}
