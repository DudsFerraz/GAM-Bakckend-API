package br.org.gam.api.Entities.account.services.getAccountById;

import br.org.gam.api.Entities.account.MyEmail;
import br.org.gam.api.common.PermissionLevelEnum;

import java.util.UUID;

public record GetAccountByIdDTO(
        UUID id,
        MyEmail email,
        String displayName,
        PermissionLevelEnum permissionLevel
) {
}
