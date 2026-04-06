package br.org.gam.api.Entities.events.oratorio.services;

import br.org.gam.api.Entities.events.generic.services.EventRDTO;
import br.org.gam.api.Entities.member.services.MemberRDTO;
import br.org.gam.api.Entities.oratoriano.services.OratorianoRDTO;

import java.util.Set;
import java.util.UUID;

public record OratorioRDTO(
        UUID id,
        EventRDTO event,
        String cancellationReason,
        Set<MemberRDTO> lancheMembers,
        Set<MemberRDTO> btJovensMembers,
        Set<MemberRDTO> btCriancasMembers,
        Set<OratorianoRDTO> oratorianos
) {
}
