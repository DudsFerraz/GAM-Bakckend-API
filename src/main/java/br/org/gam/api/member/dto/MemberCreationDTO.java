package br.org.gam.api.member.dto;

import br.org.gam.api.member.PhoneNumber;

import java.time.LocalDate;

public record MemberCreationDTO(
        String name,
        LocalDate birthDate,
        PhoneNumber phoneNumber
) {
}
