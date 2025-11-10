package br.org.gam.api.Entities.member.services.getMemberById;

import br.org.gam.api.Entities.member.MemberStatusEnum;
import br.org.gam.api.common.myPhoneNumber.MyPhoneNumber;

import java.time.LocalDate;
import java.util.UUID;

public record GetMemberByIdDTO(
        UUID id,
        UUID accountId,
        String name,
        LocalDate birthDate,
        MyPhoneNumber phoneNumber,
        MemberStatusEnum status
) {
}
