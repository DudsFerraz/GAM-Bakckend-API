package br.org.gam.api.Entities.presence.services.registerPresence;

import br.org.gam.api.Entities.event.Event;
import br.org.gam.api.Entities.event.services.getEventInstance.GetEventInstance;
import br.org.gam.api.Entities.member.Member;
import br.org.gam.api.Entities.member.services.getMemberInstance.GetMemberInstance;
import br.org.gam.api.Entities.presence.PresenceMapper;
import br.org.gam.api.Entities.presence.Presence;
import br.org.gam.api.Entities.presence.exception.PresenceNotFoundException;
import br.org.gam.api.Entities.presence.persistence.PresenceRepository;
import br.org.gam.api.Entities.presence.persistence.PresenceEntity;
import org.springframework.stereotype.Service;

@Service
public class SpringRegisterPresence implements RegisterPresence {
    private final PresenceRepository presenceRepo;
    private final PresenceMapper presenceMapper;
    private final GetMemberInstance getMemberInstance;
    private final GetEventInstance getEventInstance;

    public SpringRegisterPresence(PresenceRepository presenceRepo, PresenceMapper presenceMapper, GetMemberInstance getMemberInstance, GetEventInstance getEventInstance) {
        this.presenceRepo = presenceRepo;
        this.presenceMapper = presenceMapper;
        this.getMemberInstance = getMemberInstance;
        this.getEventInstance = getEventInstance;
    }

    @Override
    public RegisterPresenceRDTO register(RegisterPresenceDTO dto) {
        if(presenceRepo.existsByMember_IdAndEvent_Id(dto.memberId(), dto.eventId())){
            throw new PresenceNotFoundException("Presence already registered");
        }

        Member presentMember = getMemberInstance.domainById(dto.memberId());
        Event relatedEvent = getEventInstance.domainById(dto.eventId());

        Presence newPresence = Presence.register(presentMember, relatedEvent, dto.observations());
        PresenceEntity newPresenceEntity = presenceMapper.domainToEntity(newPresence);
        PresenceEntity savedPresenceEntity = presenceRepo.save(newPresenceEntity);

        return presenceMapper.entityToRegisterPresenceRDTO(savedPresenceEntity);
    }
}
