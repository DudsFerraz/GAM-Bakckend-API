package br.org.gam.api.Entities.presence.services.getPresenceById;


import java.util.UUID;

public interface GetPresenceById {
    GetPresenceByIdDTO getPresenceById(UUID memberId, UUID eventId);
}
