package br.org.gam.api.Entities.presence.services.getPresenceInstance;

import br.org.gam.api.Entities.presence.IPresenceMapper;
import br.org.gam.api.Entities.presence.Presence;
import br.org.gam.api.Entities.presence.PresenceId;
import br.org.gam.api.Entities.presence.exception.PresenceNotFoundException;
import br.org.gam.api.Entities.presence.persistence.IPresenceRepository;
import br.org.gam.api.Entities.presence.persistence.PresenceEntity;

public class GetPresenceInstanceService implements IGetPresenceInstanceService {
    private final IPresenceRepository presenceRepo;
    private final IPresenceMapper presenceMapper;

    public GetPresenceInstanceService(IPresenceRepository presenceRepo, IPresenceMapper presenceMapper) {
        this.presenceRepo = presenceRepo;
        this.presenceMapper = presenceMapper;
    }

    @Override
    public Presence getPresenceDomainById(PresenceId id) {
        return presenceRepo.findById(id)
                .map(presenceMapper::fromEntityToDomain)
                .orElseThrow(() -> new PresenceNotFoundException("Could not find presence with id " + id));
    }

    @Override
    public PresenceEntity getPresenceEntityById(PresenceId id) {
        return presenceRepo.findById(id)
                .orElseThrow(() -> new PresenceNotFoundException("Could not find presence with id " + id));
    }
}
