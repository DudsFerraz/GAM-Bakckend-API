package br.org.gam.api.Entities.presence;

import br.org.gam.api.Entities.event.EventMapper;
import br.org.gam.api.Entities.member.MemberMapper;
import br.org.gam.api.Entities.presence.persistence.PresenceEntity;
import br.org.gam.api.Entities.presence.services.PresenceRDTO;
import br.org.gam.api.Entities.presence.services.registerPresence.RegisterPresenceRDTO;
import br.org.gam.api.common.auditing.IgnoreFullAuditFields;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {MemberMapper.class, EventMapper.class})
public interface PresenceMapper {
    @IgnoreFullAuditFields
    PresenceEntity domainToEntity(Presence presenceDomain);
    Presence entityToDomain(PresenceEntity presenceEntity);
    RegisterPresenceRDTO entityToRegisterPresenceRDTO(PresenceEntity presenceEntity);
    PresenceRDTO entityToPresenceRDTO(PresenceEntity presenceEntity);
}
