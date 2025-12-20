package br.org.gam.api.Entities.member.services.getMember;

import br.org.gam.api.Entities.member.services.MemberRDTO;

import java.util.UUID;

public interface GetMember {
    public MemberRDTO byId(UUID id);
}
