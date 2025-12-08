package br.org.gam.api.Entities.presence.services.registerPresence;

import br.org.gam.api.Entities.event.Event;
import br.org.gam.api.Entities.event.services.getEventsInstance.GetEventInstance;
import br.org.gam.api.Entities.member.Member;
import br.org.gam.api.Entities.member.services.getMemberInstance.GetMemberInstance;
import br.org.gam.api.Entities.presence.PresenceMapper;
import br.org.gam.api.Entities.presence.Presence;
import br.org.gam.api.Entities.presence.PresenceId;
import br.org.gam.api.Entities.presence.exception.PresenceNotFoundException;
import br.org.gam.api.Entities.presence.persistence.PresenceRepository;
import br.org.gam.api.Entities.presence.persistence.PresenceEntity;

public class SpringRegisterPresence implements RegisterPresence {
    private final PresenceRepository presenceRepo;
    private final PresenceMapper presenceMapper;
    private final GetMemberInstance getMemberInstanceService;
    private final GetEventInstance getEventInstanceService;

    public SpringRegisterPresence(PresenceRepository presenceRepo, PresenceMapper presenceMapper, GetMemberInstance getMemberInstanceService, GetEventInstance getEventInstanceService) {
        this.presenceRepo = presenceRepo;
        this.presenceMapper = presenceMapper;
        this.getMemberInstanceService = getMemberInstanceService;
        this.getEventInstanceService = getEventInstanceService;
    }

    @Override
    public RegisterPresenceRDTO registerPresence(RegisterPresenceDTO dto) {
        if(presenceRepo.existsById(new PresenceId(dto.memberId(), dto.eventId()))){
            throw new PresenceNotFoundException("Presence already registered");
        }

        Member presentMember = getMemberInstanceService.getMemberDomainById(dto.memberId());
        Event relatedEvent = getEventInstanceService.getEventDomainById(dto.eventId());

        Presence newPresence = Presence.register(presentMember, relatedEvent, dto.observations());
        PresenceEntity newPresenceEntity = presenceMapper.fromDomainToEntity(newPresence);
        PresenceEntity savedPresenceEntity = presenceRepo.save(newPresenceEntity);

        return presenceMapper.fromEntityToRegisterPresenceResponseDTO(savedPresenceEntity);
    }
}
