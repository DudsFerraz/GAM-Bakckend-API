package br.org.gam.api.Entities.events.missa.services.createMissa;

import br.org.gam.api.Entities.events.generic.services.createEvent.CreateEventDTO;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

public record CreateMissaDTO(
        CreateEventDTO event,
        UUID comentariosMemberId,
        UUID leitura1MemberId,
        UUID salmoMemberId,
        UUID leitura2MemberId,
        UUID precesMemberId,
        Set<UUID> acolhidaMembersIds
) {
}
