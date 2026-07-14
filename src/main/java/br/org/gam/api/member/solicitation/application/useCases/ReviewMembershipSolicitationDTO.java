package br.org.gam.api.member.solicitation.application.useCases;

import jakarta.validation.constraints.NotBlank;

public record ReviewMembershipSolicitationDTO(
        @NotBlank String reason
) {
}
