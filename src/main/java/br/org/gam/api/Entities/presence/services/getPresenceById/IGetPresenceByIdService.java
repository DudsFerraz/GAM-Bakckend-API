package br.org.gam.api.Entities.presence.services.getPresenceById;


import java.util.UUID;

public interface IGetPresenceByIdService {
    GetPresenceByIdDTO getPresenceById(UUID memberId, UUID eventId);
}
