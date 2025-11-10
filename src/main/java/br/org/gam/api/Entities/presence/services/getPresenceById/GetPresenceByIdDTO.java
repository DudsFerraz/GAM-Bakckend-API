package br.org.gam.api.Entities.presence.services.getPresenceById;

import br.org.gam.api.Entities.presence.PresenceId;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record GetPresenceByIdDTO(
        PresenceId id,
        String observations
) {
}
