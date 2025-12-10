package br.org.gam.api.Entities.event.services.createEvent;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

import java.time.Instant;
import java.util.UUID;

public record CreateEventDTO(
        @NotNull @NotBlank String title,
        @Nullable String description,
        @NotNull UUID locationId,
        @NotNull UUID requiredPermissionId,
        @NotNull Instant beginDate,
        @NotNull Instant endDate
        ) {
}
