package br.org.gam.api.Entities.events.oratorio.services.createOratorio;

import br.org.gam.api.Entities.events.generic.services.createEvent.CreateEventDTO;
import br.org.gam.api.Entities.member.persistence.MemberEntity;
import br.org.gam.api.Entities.oratoriano.Oratoriano;

import java.util.Set;
import java.util.UUID;

public record CreateOratorioDTO(
        CreateEventDTO event,
        Set<UUID> lancheMembersIds,
        Set<UUID> btJovensMembersIds,
        Set<UUID> btCriancasMembersIds,
        Set<UUID> oratorianosIds
) {
}
