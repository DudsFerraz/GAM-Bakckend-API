package br.org.gam.api.Entities.member.services.getMember;

import br.org.gam.api.Entities.account.services.getAccount.GetAccountRDTO;
import br.org.gam.api.Entities.member.MemberStatus;
import br.org.gam.api.common.myPhoneNumber.MyPhoneNumber;

import java.time.LocalDate;
import java.util.UUID;

public record GetMemberRDTO(
        UUID id,
        GetAccountRDTO account,
        String name,
        LocalDate birthDate,
        int age,
        MyPhoneNumber phoneNumber,
        MemberStatus status
) {
}
