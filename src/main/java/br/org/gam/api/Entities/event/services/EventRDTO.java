package br.org.gam.api.Entities.event.services;

import br.org.gam.api.Entities.RBAC.permission.services.PermissionRDTO;
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
        Instant endDate
) {
}
