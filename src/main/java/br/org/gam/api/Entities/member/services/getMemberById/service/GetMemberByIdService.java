package br.org.gam.api.Entities.member.services.getMemberById.service;

import br.org.gam.api.Entities.member.common.IMemberMapper;
import br.org.gam.api.Entities.member.persistence.MemberEntity;
import br.org.gam.api.Entities.member.services.getMemberById.dto.GetMemberByIdDTO;
import br.org.gam.api.Entities.member.services.getMemberInstance.GetMemberInstanceService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetMemberByIdService implements IGetMemberByIdService {

    private final GetMemberInstanceService getMemberInstanceService;
    private final IMemberMapper memberMapper;

    public GetMemberByIdService(GetMemberInstanceService getMemberInstanceService, IMemberMapper memberMapper) {
        this.getMemberInstanceService = getMemberInstanceService;
        this.memberMapper = memberMapper;
    }

    @Override
    public GetMemberByIdDTO getMemberById(UUID id) {

        MemberEntity memberEntity = getMemberInstanceService.getMemberEntityById(id);
        return memberMapper.fromEntityToGetMemberByIdDTO(memberEntity);
    }
}
