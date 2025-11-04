package br.org.gam.api.Entities.member.domain;

import br.org.gam.api.Entities.account.domain.Account;
import br.org.gam.api.Entities.member.MemberStatusEnum;
import br.org.gam.api.Entities.member.PhoneNumber;
import java.time.LocalDate;
import java.util.UUID;

public class Member {
    private UUID id;
    private Account account;
    private String name;

    private LocalDate birthDate;

    private PhoneNumber phoneNumber;

    private MemberStatusEnum status;
}
