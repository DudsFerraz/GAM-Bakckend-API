package br.org.gam.api.rbac.accountRole.application.useCases;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record AddAccountRoleDTO(
        @NotNull UUID roleId,
        @NotNull @NotBlank String reason
) {
}
