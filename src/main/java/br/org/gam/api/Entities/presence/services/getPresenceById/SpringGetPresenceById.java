package br.org.gam.api.Entities.presence.services.getPresenceById;

import br.org.gam.api.Entities.presence.PresenceMapper;
import br.org.gam.api.Entities.presence.PresenceId;
import br.org.gam.api.Entities.presence.persistence.PresenceEntity;
import br.org.gam.api.Entities.presence.services.getPresenceInstance.GetPresenceInstance;

import java.util.UUID;

public class SpringGetPresenceById implements GetPresenceById {
    private final GetPresenceInstance getPresenceInstanceService;
    private final PresenceMapper presenceMapper;

    public SpringGetPresenceById(GetPresenceInstance getPresenceInstanceService, PresenceMapper presenceMapper) {
        this.getPresenceInstanceService = getPresenceInstanceService;
        this.presenceMapper = presenceMapper;
    }

    @Override
    public GetPresenceByIdDTO getPresenceById(UUID memberId, UUID eventId) {

        PresenceId presenceId = new PresenceId(memberId, eventId);
        PresenceEntity presenceEntity = getPresenceInstanceService.getPresenceEntityById(presenceId);
        return presenceMapper.fromEntityToGetPresenceByIdDTO(presenceEntity);
    }
}
