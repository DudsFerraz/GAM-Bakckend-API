package br.org.gam.api.Entities.member.domain;

import br.org.gam.api.Entities.account.domain.Account;
import br.org.gam.api.Entities.member.common.MemberStatusEnum;
import br.org.gam.api.common.Name;
import br.org.gam.api.common.myPhoneNumber.MyPhoneNumber;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Member {
    private UUID id;
    private Account account;
    private Name name;
    private LocalDate birthDate;
    private MyPhoneNumber phoneNumber;
    private MemberStatusEnum status;

    private Member(Account account, Name name, LocalDate birthDate, MyPhoneNumber phoneNumber, MemberStatusEnum status) {
        this.account = account;
        this.name = name;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    public static Member register(Account account, Name name, LocalDate birthDate, MyPhoneNumber phoneNumber){
        Objects.requireNonNull(account, "Account cannot be null.");
        Objects.requireNonNull(name, "Name cannot be null.");
        Objects.requireNonNull(birthDate, "Birth date cannot be null.");
        Objects.requireNonNull(phoneNumber, "Phone number cannot be null.");
        if (birthDate.isAfter(LocalDate.now())) throw new IllegalArgumentException("Birth date cannot be in the future.");


        MemberStatusEnum status = MemberStatusEnum.PENDENT;

        return new Member(account, name, birthDate, phoneNumber, status);
    }

    public void activate(){
        this.status = MemberStatusEnum.ACTIVE;
    }

    public void deactivate(){
        this.status = MemberStatusEnum.INACTIVE;
    }

    public UUID getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public Name getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public MyPhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public MemberStatusEnum getStatus() {
        return status;
    }

}
