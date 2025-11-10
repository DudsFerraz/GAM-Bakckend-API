package br.org.gam.api.Entities.presence.services.registerPresence;

import br.org.gam.api.Entities.event.Event;
import br.org.gam.api.Entities.event.services.getEventsInstance.IGetEventInstanceService;
import br.org.gam.api.Entities.member.Member;
import br.org.gam.api.Entities.member.services.getMemberInstance.IGetMemberInstanceService;
import br.org.gam.api.Entities.presence.IPresenceMapper;
import br.org.gam.api.Entities.presence.Presence;
import br.org.gam.api.Entities.presence.PresenceId;
import br.org.gam.api.Entities.presence.exception.PresenceNotFoundException;
import br.org.gam.api.Entities.presence.persistence.IPresenceRepository;
import br.org.gam.api.Entities.presence.persistence.PresenceEntity;

public class RegisterPresenceService implements IRegisterPresenceService {
    private final IPresenceRepository presenceRepo;
    private final IPresenceMapper presenceMapper;
    private final IGetMemberInstanceService getMemberInstanceService;
    private final IGetEventInstanceService getEventInstanceService;

    public RegisterPresenceService(IPresenceRepository presenceRepo, IPresenceMapper presenceMapper, IGetMemberInstanceService getMemberInstanceService, IGetEventInstanceService getEventInstanceService) {
        this.presenceRepo = presenceRepo;
        this.presenceMapper = presenceMapper;
        this.getMemberInstanceService = getMemberInstanceService;
        this.getEventInstanceService = getEventInstanceService;
    }

    @Override
    public RegisterPresenceResponseDTO registerPresence(RegisterPresenceDTO dto) {
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
