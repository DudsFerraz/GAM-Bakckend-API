package br.org.gam.api.Entities.presence.services.getPresence;

import java.util.UUID;

public record GetPresenceRDTO(
        UUID id,
        String observations
) {
}
