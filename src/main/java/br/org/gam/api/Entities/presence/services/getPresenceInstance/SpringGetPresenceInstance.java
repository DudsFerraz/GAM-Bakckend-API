package br.org.gam.api.Entities.presence.services.getPresenceInstance;

import br.org.gam.api.Entities.presence.PresenceMapper;
import br.org.gam.api.Entities.presence.Presence;
import br.org.gam.api.Entities.presence.exception.PresenceNotFoundException;
import br.org.gam.api.Entities.presence.persistence.PresenceRepository;
import br.org.gam.api.Entities.presence.persistence.PresenceEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpringGetPresenceInstance implements GetPresenceInstance {
    private final PresenceRepository presenceRepo;
    private final PresenceMapper presenceMapper;

    public SpringGetPresenceInstance(PresenceRepository presenceRepo, PresenceMapper presenceMapper) {
        this.presenceRepo = presenceRepo;
        this.presenceMapper = presenceMapper;
    }

    @Override
    public Presence domainById(UUID id) {
        return presenceRepo.findById(id)
                .map(presenceMapper::fromEntityToDomain)
                .orElseThrow(() -> new PresenceNotFoundException("Could not find presence with id " + id));
    }

    @Override
    public PresenceEntity entityById(UUID id) {
        return presenceRepo.findById(id)
                .orElseThrow(() -> new PresenceNotFoundException("Could not find presence with id " + id));
    }

    @Override
    public PresenceEntity entityByIds(UUID memberId, UUID eventId) {
        return presenceRepo.findByMember_IdAndEvent_Id(memberId, eventId)
                .orElseThrow(() -> new PresenceNotFoundException(
                        String.format("member with id: %s has no presence registered in event with id: %s", memberId, eventId)
                ));
    }

    @Override
    public Presence domainByIds(UUID memberId, UUID eventId) {
        return presenceRepo.findByMember_IdAndEvent_Id(memberId, eventId)
                .map(presenceMapper::fromEntityToDomain)
                .orElseThrow(() -> new PresenceNotFoundException(
                        String.format("member with id: %s has no presence registered in event with id: %s", memberId, eventId)
                ));
    }
}
