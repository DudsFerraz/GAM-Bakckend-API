package br.org.gam.api.Entities.member.services.activation;

import br.org.gam.api.Entities.member.common.IMemberMapper;
import br.org.gam.api.Entities.member.domain.Member;
import br.org.gam.api.Entities.member.persistence.IMemberRepository;
import br.org.gam.api.Entities.member.persistence.MemberEntity;
import br.org.gam.api.Entities.member.services.getMemberInstance.IGetMemberInstanceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.function.Consumer;

@Service
public class ActivationService implements IActivationService {

    private final IMemberRepository memberRepo;
    private final IGetMemberInstanceService getMemberInstanceService;
    private final IMemberMapper memberMapper;

    public ActivationService(IMemberRepository memberRepo, IGetMemberInstanceService getMemberInstanceService, IMemberMapper memberMapper) {
        this.memberRepo = memberRepo;
        this.getMemberInstanceService = getMemberInstanceService;
        this.memberMapper = memberMapper;
    }

    @Transactional
    @Override
    public void activate(UUID memberId) {
        changeStatus(memberId, Member::activate);
    }

    @Transactional
    @Override
    public void deactivate(UUID memberId) {
        changeStatus(memberId, Member::deactivate);
    }

    private void changeStatus(UUID memberId, Consumer<Member> consumer) {
        Member member = getMemberInstanceService.getMemberDomainById(memberId);
        consumer.accept(member);

        MemberEntity memberEntity = memberMapper.fromDomainToEntity(member);
        memberRepo.save(memberEntity);
    }
}
