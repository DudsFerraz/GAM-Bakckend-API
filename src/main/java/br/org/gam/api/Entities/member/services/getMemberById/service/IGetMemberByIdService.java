package br.org.gam.api.Entities.member.services.getMemberById.service;

import br.org.gam.api.Entities.member.services.getMemberById.dto.GetMemberByIdDTO;

import java.util.UUID;

public interface IGetMemberByIdService {
    public GetMemberByIdDTO getMemberById(UUID id);
}
