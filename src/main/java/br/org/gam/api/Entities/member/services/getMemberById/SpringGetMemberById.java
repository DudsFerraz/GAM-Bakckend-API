package br.org.gam.api.Entities.member.services.getMemberById;

import br.org.gam.api.Entities.member.MemberMapper;
import br.org.gam.api.Entities.member.persistence.MemberEntity;
import br.org.gam.api.Entities.member.services.getMemberInstance.SpringGetMemberInstance;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpringGetMemberById implements GetMemberById {

    private final SpringGetMemberInstance springGetMemberInstance;
    private final MemberMapper memberMapper;

    public SpringGetMemberById(SpringGetMemberInstance springGetMemberInstance, MemberMapper memberMapper) {
        this.springGetMemberInstance = springGetMemberInstance;
        this.memberMapper = memberMapper;
    }

    @Override
    public GetMemberByIdDTO getMemberById(UUID id) {

        MemberEntity memberEntity = springGetMemberInstance.getMemberEntityById(id);
        return memberMapper.fromEntityToGetMemberByIdDTO(memberEntity);
    }
}
