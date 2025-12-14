package br.org.gam.api.Entities.event.services.getEvent;

import br.org.gam.api.Entities.RBAC.permission.services.getPermission.GetPermissionRDTO;
import br.org.gam.api.Entities.location.services.getLocation.GetLocationRDTO;

import java.time.Instant;
import java.util.UUID;

public record GetEventRDTO(
        UUID id,
        String title,
        String description,
        GetLocationRDTO location,
        GetPermissionRDTO requiredPermission,
        Instant beginDate,
        Instant endDate
) {
}
