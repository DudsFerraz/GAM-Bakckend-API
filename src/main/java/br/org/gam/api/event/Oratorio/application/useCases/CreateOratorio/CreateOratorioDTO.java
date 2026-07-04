package br.org.gam.api.event.oratorio.application.useCases.createOratorio;

import br.org.gam.api.event.application.useCases.createEvent.CreateEventDTO;
import br.org.gam.api.event.oratorio.domain.Oratorio;
import br.org.gam.api.member.persistence.MemberEntity;
import br.org.gam.api.oratoriano.domain.Oratoriano;
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
