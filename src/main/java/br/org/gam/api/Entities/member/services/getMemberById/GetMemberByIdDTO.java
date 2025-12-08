package br.org.gam.api.Entities.member.services.getMemberById;

import br.org.gam.api.Entities.member.MemberStatus;
import br.org.gam.api.common.myPhoneNumber.MyPhoneNumber;

import java.time.LocalDate;
import java.util.UUID;

public record GetMemberByIdDTO(
        UUID id,
        UUID accountId,
        String name,
        LocalDate birthDate,
        MyPhoneNumber phoneNumber,
        MemberStatus status
) {
}
