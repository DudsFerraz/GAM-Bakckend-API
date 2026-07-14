package br.org.gam.api.member.application.useCases;

import br.org.gam.api.member.application.MemberMapper;
import br.org.gam.api.member.application.MemberDomainLoader;
import br.org.gam.api.member.application.MemberRoleProjection;
import br.org.gam.api.member.domain.Member;
import br.org.gam.api.member.domain.MemberStatus;
import br.org.gam.api.member.persistence.MemberEntity;
import br.org.gam.api.member.persistence.MemberRepository;
import br.org.gam.api.rbac.role.domain.SystemRole;
import br.org.gam.api.shared.activitylog.ActivityEvents;
import br.org.gam.api.shared.exception.ConflictException;
import br.org.gam.api.shared.validation.RequiredReason;
import jakarta.transaction.Transactional;
import java.util.function.Consumer;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class Activation {

    private final MemberRepository memberRepo;
    private final MemberDomainLoader getMemberInstance;
    private final MemberMapper memberMapper;
    private final MemberRoleProjection memberRoleProjection;
    private final ActivityEvents activityEvents;

    public Activation(MemberRepository memberRepo, MemberDomainLoader getMemberInstance, MemberMapper memberMapper,
                      MemberRoleProjection memberRoleProjection, ActivityEvents activityEvents) {
        this.memberRepo = memberRepo;
        this.getMemberInstance = getMemberInstance;
        this.memberMapper = memberMapper;
        this.memberRoleProjection = memberRoleProjection;
        this.activityEvents = activityEvents;
    }

    @Transactional
    public void activate(UUID memberId, String reason) {
        String auditReason = RequiredReason.normalize(reason, "Member activation requires an audit reason.");
        MemberStatusChange change = changeStatus(
                  memberId,
                MemberStatus.INACTIVE,
                Member::activate,
                SystemRole.MEMBER.getCode(),
                SystemRole.VISITOR.getCode()
        );
        activityEvents.memberActivated(
                change.memberId(),
                change.accountId(),
                change.previousStatus(),
                change.newStatus(),
                change.roleAdded(),
                change.roleRemoved(),
                auditReason
        );
    }

    @Transactional
    public void deactivate(UUID memberId, String reason) {
        String auditReason = RequiredReason.normalize(reason, "Member deactivation requires an audit reason.");
        MemberStatusChange change = changeStatus(
                  memberId,
                MemberStatus.ACTIVE,
                Member::deactivate,
                SystemRole.VISITOR.getCode(),
                SystemRole.MEMBER.getCode()
        );
        activityEvents.memberDeactivated(
                change.memberId(),
                change.accountId(),
                change.previousStatus(),
                change.newStatus(),
                change.roleAdded(),
                change.roleRemoved(),
                auditReason
        );
    }


    private MemberStatusChange changeStatus(UUID memberId,
                                            MemberStatus requiredStatus,
                                            Consumer<Member> memberConsumer, String roleToAdd,
                                            String roleToRemove) {
        Member member = getMemberInstance.requiredById(memberId);
        if (member.getStatus() != requiredStatus) {
            throw ConflictException.resource("Member", memberId, "Member is already in the requested status.");
        }
        String previousStatus = member.getStatus().name();
        memberConsumer.accept(member);

        UUID accountId = member.getAccount().getId();

        if (member.getStatus() == MemberStatus.ACTIVE) {
            memberRoleProjection.synchronizeActive(accountId);
        } else {
            memberRoleProjection.synchronizeInactive(accountId);
        }

        MemberEntity memberEntity = memberMapper.domainToEntity(member);
        memberRepo.save(memberEntity);

        return new MemberStatusChange(
                member.getId(),
                accountId,
                previousStatus,
                member.getStatus().name(),
                roleToAdd,
                roleToRemove
        );
    }

    private record MemberStatusChange(
            UUID memberId,
            UUID accountId,
            String previousStatus,
            String newStatus,
            String roleAdded,
            String roleRemoved
    ) {
    }

}
