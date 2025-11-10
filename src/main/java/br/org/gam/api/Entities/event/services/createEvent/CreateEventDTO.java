package br.org.gam.api.Entities.event.services.createEvent;

import br.org.gam.api.common.PermissionLevelEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

import java.time.Instant;
import java.util.UUID;

public record CreateEventDTO(
        @NotNull @NotBlank String title,
        @Nullable String description,
        @Nullable UUID locationId,
        @NotNull PermissionLevelEnum requiredPermissionLevel,
        @NotNull Instant beginDate,
        @NotNull Instant endDate
        ) {
}
