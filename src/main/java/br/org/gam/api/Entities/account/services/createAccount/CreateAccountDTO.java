package br.org.gam.api.Entities.account.services.createAccount;

import br.org.gam.api.Entities.account.MyEmail;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateAccountDTO(
        @NotNull @Valid MyEmail email,

        @NotNull @NotBlank String password,

        @NotNull @NotBlank String displayName) {
}
