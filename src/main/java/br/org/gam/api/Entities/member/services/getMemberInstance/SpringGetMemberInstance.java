package br.org.gam.api.Entities.member.services.getMemberInstance;

import br.org.gam.api.Entities.member.MemberMapper;
import br.org.gam.api.Entities.member.Member;
import br.org.gam.api.Entities.member.exception.MemberNotFoundException;
import br.org.gam.api.Entities.member.persistence.MemberRepository;
import br.org.gam.api.Entities.member.persistence.MemberEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpringGetMemberInstance implements GetMemberInstance {

    private final MemberRepository memberRepo;
    private final MemberMapper memberMapper;

    public SpringGetMemberInstance(MemberRepository memberRepo, MemberMapper memberMapper) {
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
