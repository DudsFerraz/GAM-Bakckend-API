package br.org.gam.api.Entities.events.core.services;

import br.org.gam.api.Entities.RBAC.permission.services.PermissionRDTO;
import br.org.gam.api.Entities.events.core.EventStatus;
import br.org.gam.api.Entities.events.core.EventType;
import br.org.gam.api.Entities.location.services.LocationRDTO;

import java.time.Instant;
import java.util.UUID;

public record EventRDTO(
        UUID id,
        String title,
        String description,
        LocationRDTO location,
        PermissionRDTO requiredPermission,
        Instant beginDate,
        Instant endDate,
        EventType type,
        EventStatus status
) {
}
