package br.org.gam.api.Entities.presence.services;

import br.org.gam.api.Entities.events.generic.services.EventRDTO;
import br.org.gam.api.Entities.member.services.MemberRDTO;

import java.util.UUID;

public record PresenceRDTO(
        UUID id,
        MemberRDTO member,
        EventRDTO event,
        String observations
) {
}
