package br.org.gam.api.Entities.member.services.activation;

import br.org.gam.api.Entities.RBAC.accountRole.services.addAccountRole.AddAccountRole;
import br.org.gam.api.Entities.RBAC.accountRole.services.dropAccountRole.DropAccountRole;
import br.org.gam.api.Entities.member.Member;
import br.org.gam.api.Entities.member.MemberMapper;
import br.org.gam.api.Entities.member.persistence.MemberEntity;
import br.org.gam.api.Entities.member.persistence.MemberRepository;
import br.org.gam.api.Entities.member.services.getMemberInstance.GetMemberInstance;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Consumer;

@Service
public class SpringActivation implements Activation {

    private final MemberRepository memberRepo;
    private final GetMemberInstance getMemberInstance;
    private final MemberMapper memberMapper;
    private final AddAccountRole addAccountRole;
    private final DropAccountRole dropAccountRole;
    public SpringActivation(MemberRepository memberRepo, GetMemberInstance getMemberInstance, MemberMapper memberMapper, AddAccountRole addAccountRole, DropAccountRole dropAccountRole) {
        this.memberRepo = memberRepo;
        this.getMemberInstance = getMemberInstance;
        this.memberMapper = memberMapper;
        this.addAccountRole = addAccountRole;
        this.dropAccountRole = dropAccountRole;
    }

    @Transactional
    @Override
    public void activate(UUID memberId) {
        changeStatus(memberId, Member::activate, "MEMBER", "VISITOR");
    }

    @Transactional
    @Override
    public void deactivate(UUID memberId) {
        changeStatus(memberId, Member::deactivate, "VISITOR", "MEMBER");
    }


    private void changeStatus(UUID memberId, Consumer<Member> memberConsumer, String roleToAdd, String roleToRemove) {
        Member member = getMemberInstance.domainById(memberId);
        memberConsumer.accept(member);

        UUID accountId = member.getAccount().getId();

        addAccountRole.byRoleName(roleToAdd, accountId);
        dropAccountRole.byRoleName(roleToRemove, accountId);

        MemberEntity memberEntity = memberMapper.fromDomainToEntity(member);
        memberRepo.save(memberEntity);
    }

}
