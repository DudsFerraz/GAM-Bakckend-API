package br.org.gam.api.Entities.account.services.getAccountById;

import br.org.gam.api.Entities.account.Email;
import br.org.gam.api.common.PermissionLevelEnum;

import java.util.UUID;

public record GetAccountByIdDTO(
        UUID id,
        Email email,
        String displayName,
        PermissionLevelEnum permissionLevel
) {
}
