package br.org.gam.api.Entities.member.services.getMember;

import br.org.gam.api.Entities.member.MemberMapper;
import br.org.gam.api.Entities.member.persistence.MemberEntity;
import br.org.gam.api.Entities.member.services.getMemberInstance.GetMemberInstance;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpringGetMember implements GetMember {
    private final GetMemberInstance getMemberInstance;
    private final MemberMapper memberMapper;

    public SpringGetMember(GetMemberInstance getMemberInstance, MemberMapper memberMapper) {
        this.getMemberInstance = getMemberInstance;
        this.memberMapper = memberMapper;
    }

    @Override
    public GetMemberRDTO byId(UUID id) {

        MemberEntity memberEntity = getMemberInstance.entityById(id);
        return memberMapper.fromEntityToGetMemberRDTO(memberEntity);
    }
}
