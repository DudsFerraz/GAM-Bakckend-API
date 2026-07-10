package br.org.gam.api.account.application;

import br.org.gam.api.shared.domain.GamEmail;
import br.org.gam.api.rbac.accountRole.application.AccountRolesRDTO;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.UUID;

public record AccountRDTO(
        UUID id,
        GamEmail email,
        String displayName,
        @JsonSerialize(using = AccountRolesSerializer.class)
        AccountRolesRDTO roles
) {
    public AccountRDTO {
        roles = roles == null ? new AccountRolesRDTO() : roles;
    }
}
