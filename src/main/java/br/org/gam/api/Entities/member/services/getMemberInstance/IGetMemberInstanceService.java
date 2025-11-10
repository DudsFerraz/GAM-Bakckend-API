package br.org.gam.api.Entities.member.services.getMemberInstance;

import br.org.gam.api.Entities.member.Member;
import br.org.gam.api.Entities.member.persistence.MemberEntity;

import java.util.UUID;

public interface IGetMemberInstanceService {
    public Member getMemberDomainById(UUID id);
    public MemberEntity getMemberEntityById(UUID id);
}
