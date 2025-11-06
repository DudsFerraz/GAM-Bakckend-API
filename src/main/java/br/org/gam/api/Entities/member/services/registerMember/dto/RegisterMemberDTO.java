package br.org.gam.api.Entities.member.services.registerMember.dto;

import br.org.gam.api.common.myPhoneNumber.MyPhoneNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record RegisterMemberDTO(
        @NotNull UUID accountId,
        @NotNull @NotBlank String firstName,
        @NotNull @NotBlank String surname,
        @NotNull LocalDate birthDate,
        @NotNull MyPhoneNumber phoneNumber
) {
}
