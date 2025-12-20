package br.org.gam.api.Entities.member.services;

import br.org.gam.api.Entities.account.services.AccountRDTO;
import br.org.gam.api.Entities.member.MemberStatus;
import br.org.gam.api.common.myPhoneNumber.MyPhoneNumber;

import java.time.LocalDate;
import java.util.UUID;

public record MemberRDTO(
        UUID id,
        AccountRDTO account,
        String name,
        LocalDate birthDate,
        int age,
        MyPhoneNumber phoneNumber,
        MemberStatus status
) {
}
