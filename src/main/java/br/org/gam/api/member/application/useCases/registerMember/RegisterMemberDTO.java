package br.org.gam.api.member.application.useCases.registerMember;

import br.org.gam.api.shared.phonenumber.GamPhoneNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

public record RegisterMemberDTO(
        @NotNull UUID accountId,
        @NotNull @NotBlank String firstName,
        @NotNull @NotBlank String surname,
        @NotNull LocalDate birthDate,
        @NotNull GamPhoneNumber phoneNumber,
        String reason
) {
    public RegisterMemberDTO(UUID accountId, String firstName, String surname, LocalDate birthDate,
                             GamPhoneNumber phoneNumber) {
        this(accountId, firstName, surname, birthDate, phoneNumber, null);
    }
}
