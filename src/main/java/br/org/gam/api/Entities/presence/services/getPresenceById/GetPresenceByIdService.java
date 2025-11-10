package br.org.gam.api.Entities.presence.services.getPresenceById;

import br.org.gam.api.Entities.presence.IPresenceMapper;
import br.org.gam.api.Entities.presence.PresenceId;
import br.org.gam.api.Entities.presence.persistence.PresenceEntity;
import br.org.gam.api.Entities.presence.services.getPresenceInstance.IGetPresenceInstanceService;

import java.util.UUID;

public class GetPresenceByIdService implements IGetPresenceByIdService {
    private final IGetPresenceInstanceService getPresenceInstanceService;
    private final IPresenceMapper presenceMapper;

    public GetPresenceByIdService(IGetPresenceInstanceService getPresenceInstanceService, IPresenceMapper presenceMapper) {
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
