package br.org.gam.api.Entities.presence.services.getPresenceInstance;

import br.org.gam.api.Entities.presence.PresenceMapper;
import br.org.gam.api.Entities.presence.Presence;
import br.org.gam.api.Entities.presence.PresenceId;
import br.org.gam.api.Entities.presence.exception.PresenceNotFoundException;
import br.org.gam.api.Entities.presence.persistence.PresenceRepository;
import br.org.gam.api.Entities.presence.persistence.PresenceEntity;

public class SpringGetPresenceInstance implements GetPresenceInstance {
    private final PresenceRepository presenceRepo;
    private final PresenceMapper presenceMapper;

    public SpringGetPresenceInstance(PresenceRepository presenceRepo, PresenceMapper presenceMapper) {
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
