package br.org.gam.api.member.application;

import br.org.gam.api.account.application.AccountRDTO;
import br.org.gam.api.account.application.AccountSummaryRDTO;
import br.org.gam.api.member.domain.MemberStatus;
import br.org.gam.api.shared.phonenumber.GamPhoneNumber;
import java.time.LocalDate;
import java.util.UUID;

public record MemberRDTO(
        UUID id,
        AccountSummaryRDTO account,
        String firstName,
        String surname,
        LocalDate birthDate,
        GamPhoneNumber phoneNumber,
        MemberStatus status
) {
    public MemberRDTO(UUID id, AccountRDTO account, String name, LocalDate birthDate,
                      GamPhoneNumber phoneNumber, MemberStatus status) {
        this(
                id,
                account == null ? null : new AccountSummaryRDTO(account.id(), account.email(), account.displayName()),
                name,
                null,
                birthDate,
                phoneNumber,
                status
        );
    }
}
