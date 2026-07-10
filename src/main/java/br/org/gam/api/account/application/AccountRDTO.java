package br.org.gam.api.account.application;

import br.org.gam.api.shared.domain.GamEmail;
import br.org.gam.api.rbac.accountRole.application.AccountRolesRDTO;
import java.util.UUID;

public record AccountRDTO(
        UUID id,
        GamEmail email,
        String displayName,
        AccountRolesRDTO roles
) {
}
