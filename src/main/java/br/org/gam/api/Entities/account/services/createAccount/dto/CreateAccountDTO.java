package br.org.gam.api.Entities.account.services.createAccount.dto;

import br.org.gam.api.Entities.account.common.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateAccountDTO(
        @NotNull
        Email email,

        @NotBlank
        @NotNull
        String password,

        @NotBlank
        @NotNull
        String displayName) {
}
