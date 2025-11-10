package br.org.gam.api.Entities.member.services.getMemberInstance;

import br.org.gam.api.Entities.member.Member;
import br.org.gam.api.Entities.member.persistence.MemberEntity;

import java.util.UUID;

public interface IGetMemberInstanceService {
    Member getMemberDomainById(UUID id);
    MemberEntity getMemberEntityById(UUID id);
}
