package br.org.gam.api.account.application;

import br.org.gam.api.shared.domain.GamEmail;
import java.util.UUID;

public record AccountSummaryRDTO(
        UUID id,
        GamEmail email,
        String displayName
) {
}
