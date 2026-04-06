package br.org.gam.api.Entities.oratoriano.services;

import br.org.gam.api.common.myPhoneNumber.MyPhoneNumber;

import java.time.LocalDate;
import java.util.UUID;

public record OratorianoRDTO(
        UUID id,
        String name,
        LocalDate birthDate,
        MyPhoneNumber phoneNumber
) {
}
