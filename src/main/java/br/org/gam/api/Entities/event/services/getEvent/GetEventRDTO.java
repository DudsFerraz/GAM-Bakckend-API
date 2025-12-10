package br.org.gam.api.Entities.event.services.getEvent;

import br.org.gam.api.Entities.RBAC.permission.services.getPermissionById.GetPermissionByIdRDTO;
import br.org.gam.api.Entities.location.services.getLocationById.GetLocationRDTO;

import java.time.Instant;
import java.util.UUID;

public record GetEventRDTO(
        UUID id,
        String title,
        String description,
        GetLocationRDTO location,
        GetPermissionByIdRDTO requiredPermission,
        Instant beginDate,
        Instant endDate
) {
}
