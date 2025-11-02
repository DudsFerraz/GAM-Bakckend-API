package br.org.gam.api.account.dto;

import br.org.gam.api.account.Email;

public record AccountCreationDTO(Email email, String password) {
}
