package br.org.gam.api.presence.application;

import br.org.gam.api.event.domain.EventStatus;
import br.org.gam.api.event.domain.EventType;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

public record PresenceEventRDTO(
        @NotNull UUID id,
        @NotNull String title,
        @NotNull Instant beginDate,
        @NotNull Instant endDate,
        @NotNull EventType type,
        @NotNull EventStatus status
) {
}
