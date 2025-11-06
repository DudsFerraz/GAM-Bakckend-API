package br.org.gam.api.Entities.member.services.getMemberInstance;

import br.org.gam.api.Entities.member.common.IMemberMapper;
import br.org.gam.api.Entities.member.domain.Member;
import br.org.gam.api.Entities.member.exception.MemberNotFoundException;
import br.org.gam.api.Entities.member.persistence.IMemberRepository;
import br.org.gam.api.Entities.member.persistence.MemberEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetMemberInstanceService implements IGetMemberInstanceService {

    private final IMemberRepository memberRepo;
    private final IMemberMapper memberMapper;

    public GetMemberInstanceService(IMemberRepository memberRepo, IMemberMapper memberMapper) {
        this.memberRepo = memberRepo;
        this.memberMapper = memberMapper;
    }

    @Override
    public Member getMemberDomainById(UUID id) {
        return memberRepo.findById(id)
                .map(memberMapper::fromEntityToDomain)
                .orElseThrow(() -> new MemberNotFoundException("Could not find member with id " + id));
    }

    @Override
    public MemberEntity getMemberEntityById(UUID id) {
        return memberRepo.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("Could not find member with id " + id));
    }
}
