package br.org.gam.api.Entities.events.missa.services;

import br.org.gam.api.Entities.events.generic.services.EventRDTO;
import br.org.gam.api.Entities.member.services.MemberRDTO;

import java.util.Set;
import java.util.UUID;

public record MissaRDTO(
        UUID id,
        EventRDTO event,
        MemberRDTO comentariosMember,
        MemberRDTO leitura1Member,
        MemberRDTO salmoMember,
        MemberRDTO leitura2Member,
        MemberRDTO precesMember,
        Set<MemberRDTO> acolhidaMembers
) {
}
