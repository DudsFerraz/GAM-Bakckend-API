package br.org.gam.api.Entities.member.services.getMember;

import java.util.UUID;

public interface GetMember {
    public GetMemberRDTO byId(UUID id);
}
