package br.org.gam.api.Entities.member.services.getMemberById;

import java.util.UUID;

public interface GetMemberById {
    public GetMemberByIdDTO getMemberById(UUID id);
}
