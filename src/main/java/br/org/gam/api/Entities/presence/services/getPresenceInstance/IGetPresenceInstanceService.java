package br.org.gam.api.Entities.presence.services.getPresenceInstance;

import br.org.gam.api.Entities.presence.Presence;
import br.org.gam.api.Entities.presence.PresenceId;
import br.org.gam.api.Entities.presence.persistence.PresenceEntity;

public interface IGetPresenceInstanceService {
    Presence getPresenceDomainById(PresenceId id);
    PresenceEntity getPresenceEntityById(PresenceId id);
}
