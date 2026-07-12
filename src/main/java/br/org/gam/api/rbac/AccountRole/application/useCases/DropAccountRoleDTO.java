package br.org.gam.api.rbac.accountRole.application.useCases;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DropAccountRoleDTO(
        @NotNull @NotBlank String reason
) {
}
