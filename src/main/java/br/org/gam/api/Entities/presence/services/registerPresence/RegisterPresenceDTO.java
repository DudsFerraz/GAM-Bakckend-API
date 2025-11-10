package br.org.gam.api.Entities.presence.services.registerPresence;

import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

import java.util.UUID;

public record RegisterPresenceDTO(
        @NotNull UUID eventId,
        @NotNull UUID memberId,
        @Nullable String observations
) {
}
