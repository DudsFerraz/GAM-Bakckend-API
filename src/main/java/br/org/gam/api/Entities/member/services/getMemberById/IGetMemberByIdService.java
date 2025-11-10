package br.org.gam.api.Entities.member.services.getMemberById;

import java.util.UUID;

public interface IGetMemberByIdService {
    public GetMemberByIdDTO getMemberById(UUID id);
}
