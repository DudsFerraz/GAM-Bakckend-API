package br.org.gam.api.Entities.account.services.loginAccount;

import br.org.gam.api.Entities.account.MyEmail;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginAccountDTO(
        @NotNull @Valid MyEmail email,
        @NotNull @NotBlank String password) {
}
