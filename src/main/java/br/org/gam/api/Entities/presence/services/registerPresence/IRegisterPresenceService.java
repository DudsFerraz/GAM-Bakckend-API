package br.org.gam.api.Entities.presence.services.registerPresence;

import br.org.gam.api.Entities.presence.Presence;
import br.org.gam.api.Entities.presence.PresenceId;

public interface IRegisterPresenceService {
    RegisterPresenceResponseDTO registerPresence(RegisterPresenceDTO dto);
}
