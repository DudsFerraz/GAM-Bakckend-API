package br.org.gam.api.Entities.member.services.getMember;

import br.org.gam.api.Entities.member.MemberMapper;
import br.org.gam.api.Entities.member.exception.MemberNotFoundException;
import br.org.gam.api.Entities.member.persistence.MemberEntity;
import br.org.gam.api.Entities.member.security.MemberSecurity;
import br.org.gam.api.Entities.member.services.MemberRDTO;
import br.org.gam.api.Entities.member.services.getMemberInstance.GetMemberInstance;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpringGetMember implements GetMember {
    private final GetMemberInstance getMemberInstance;
    private final MemberMapper memberMapper;
    private final MemberSecurity memberSecurity;

    public SpringGetMember(GetMemberInstance getMemberInstance, MemberMapper memberMapper, MemberSecurity memberSecurity) {
        this.getMemberInstance = getMemberInstance;
        this.memberMapper = memberMapper;
        this.memberSecurity = memberSecurity;
    }

    @Override
    public MemberRDTO byId(UUID id) {
        MemberEntity memberEntity = getMemberInstance.entityById(id);
        if(!memberSecurity.canGetMember(memberEntity)) throw new MemberNotFoundException("Could not find member with id " + id);

        return memberMapper.entityToMemberRDTO(memberEntity);
    }

}
