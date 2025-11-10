package br.org.gam.api.Entities.account.services.createAccount;

import br.org.gam.api.Entities.account.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateAccountDTO(
        @NotNull Email email,

        @NotNull @NotBlank String password,

        @NotNull @NotBlank String displayName) {
}
