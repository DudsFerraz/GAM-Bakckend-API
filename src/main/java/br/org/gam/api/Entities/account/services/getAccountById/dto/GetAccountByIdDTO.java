package br.org.gam.api.Entities.account.services.getAccountById.dto;

import br.org.gam.api.Entities.account.common.Email;
import br.org.gam.api.Entities.account.common.PermissionLevelEnum;

import java.util.UUID;

public record GetAccountByIdDTO(
        UUID id,
        Email email,
        String displayName,
        PermissionLevelEnum permissionLevel

) {
}
