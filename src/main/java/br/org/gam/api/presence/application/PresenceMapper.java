package br.org.gam.api.presence.application;

import br.org.gam.api.event.application.EventMapper;
import br.org.gam.api.member.application.MemberMapper;
import br.org.gam.api.presence.application.useCases.registerPresence.RegisterPresenceRDTO;
import br.org.gam.api.presence.persistence.PresenceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {MemberMapper.class, EventMapper.class})
public interface PresenceMapper {

    // =====================================================================================
    // Persistence -> RDTO
    // =====================================================================================

    default RegisterPresenceRDTO entityToRegisterPresenceRDTO(PresenceEntity presenceEntity) {
        var member = presenceEntity.getMember();
        var event = presenceEntity.getEvent();
        return new RegisterPresenceRDTO(
                presenceEntity.getId(),
                new PresenceMemberRDTO(
                        member.getId(),
                        member.getName().firstName(),
                        member.getName().surname(),
                        member.getStatus()
                ),
                new PresenceEventRDTO(
                        event.getId(),
                        event.getTitle(),
                        event.getBeginDate(),
                        event.getEndDate(),
                        event.getType(),
                        event.getStatus()
                ),
                presenceEntity.getObservations(),
                presenceEntity.getCreatedAt()
        );
    }

    PresenceRDTO entityToRDTO(PresenceEntity presenceEntity);
}
