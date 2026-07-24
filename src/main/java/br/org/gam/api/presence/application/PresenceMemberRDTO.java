package br.org.gam.api.presence.application;

import br.org.gam.api.member.domain.MemberStatus;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record PresenceMemberRDTO(
        @NotNull UUID id,
        @NotNull String firstName,
        @NotNull String surname,
        @NotNull MemberStatus status
) {
}
