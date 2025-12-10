package br.org.gam.api.Entities.presence.services.getPresenceInstance;

import br.org.gam.api.Entities.presence.Presence;
import br.org.gam.api.Entities.presence.persistence.PresenceEntity;

import java.util.UUID;

public interface GetPresenceInstance {
    Presence domainById(UUID id);
    PresenceEntity entityById(UUID id);
    PresenceEntity entityByIds(UUID memberId, UUID eventId);
    Presence domainByIds(UUID memberId, UUID eventId);
}
