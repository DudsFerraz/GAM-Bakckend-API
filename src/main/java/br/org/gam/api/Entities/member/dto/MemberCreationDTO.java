package br.org.gam.api.Entities.member.dto;

import br.org.gam.api.Entities.member.PhoneNumber;

import java.time.LocalDate;

public record MemberCreationDTO(
        String name,
        LocalDate birthDate,
        PhoneNumber phoneNumber
) {
}
