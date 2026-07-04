package br.org.gam.api.event.missa.application.useCases.createMissa;

import br.org.gam.api.event.missa.domain.Missa;
import java.util.UUID;

public record CreateMissaRDTO(
        UUID id
) {
}
