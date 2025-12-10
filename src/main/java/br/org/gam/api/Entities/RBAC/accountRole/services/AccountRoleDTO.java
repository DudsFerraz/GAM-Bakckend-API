package br.org.gam.api.Entities.RBAC.accountRole.services;

import java.util.UUID;

public record AccountRoleDTO(
        UUID accountId,
        UUID roleId
) {
}
