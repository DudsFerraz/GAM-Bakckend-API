package br.org.gam.api.member.domain;

import br.org.gam.api.account.domain.Account;
import br.org.gam.api.member.MemberStatusEnum;
import br.org.gam.api.member.PhoneNumber;
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
