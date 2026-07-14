package br.org.gam.api.member.solicitation.application;

import br.org.gam.api.account.application.AccountSummaryRDTO;
import br.org.gam.api.member.solicitation.domain.MembershipSolicitationStatus;
import br.org.gam.api.shared.phonenumber.GamPhoneNumber;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record MembershipSolicitationRDTO(
        UUID id,
        AccountSummaryRDTO account,
        String firstName,
        String surname,
        LocalDate birthDate,
        GamPhoneNumber phoneNumber,
        String justification,
        MembershipSolicitationStatus status,
        Instant submittedAt,
        AccountSummaryRDTO reviewedBy,
        Instant decidedAt,
        String reviewReason,
        UUID memberId
) {
}
