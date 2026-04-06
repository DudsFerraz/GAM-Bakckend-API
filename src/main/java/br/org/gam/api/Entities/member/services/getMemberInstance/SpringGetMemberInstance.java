package br.org.gam.api.Entities.member.services.getMemberInstance;

import br.org.gam.api.Entities.member.MemberMapper;
import br.org.gam.api.Entities.member.Member;
import br.org.gam.api.Entities.member.exception.MemberNotFoundException;
import br.org.gam.api.Entities.member.persistence.MemberRepository;
import br.org.gam.api.Entities.member.persistence.MemberEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SpringGetMemberInstance implements GetMemberInstance {

    private final MemberRepository memberRepo;
    private final MemberMapper memberMapper;

    public SpringGetMemberInstance(MemberRepository memberRepo, MemberMapper memberMapper) {
        this.memberRepo = memberRepo;
        this.memberMapper = memberMapper;
    }

    @Override
    public Member domainById(UUID id) {
        return memberRepo.findById(id)
                .map(memberMapper::entityToDomain)
                .orElseThrow(() -> new MemberNotFoundException("Could not find member with id " + id));
    }

    @Override
    public MemberEntity entityById(UUID id) {
        return memberRepo.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("Could not find member with id " + id));
    }

    @Override
    public Set<Member> domainsById(Set<UUID> ids) {
        if (ids == null || ids.isEmpty()) return new HashSet<>();

        Set<UUID> safeIds = ids.stream()
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toSet());

        return memberRepo.findAllById(safeIds)
                .stream()
                .map(memberMapper::entityToDomain)
                .collect(Collectors.toSet());
    }
}
