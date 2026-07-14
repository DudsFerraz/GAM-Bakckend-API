package br.org.gam.api.member.solicitation.application.useCases;

import br.org.gam.api.member.solicitation.application.MembershipSolicitationMapper;
import br.org.gam.api.member.solicitation.application.MembershipSolicitationRDTO;
import br.org.gam.api.member.solicitation.persistence.MembershipSolicitationEntity;
import br.org.gam.api.member.solicitation.persistence.MembershipSolicitationRepository;
import br.org.gam.api.rbac.permission.domain.PermissionEnum;
import br.org.gam.api.security.SecurityUtils;
import br.org.gam.api.shared.exception.NotFoundException;
import java.util.UUID;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetMembershipSolicitation {
    private final MembershipSolicitationRepository solicitationRepo;
    private final MembershipSolicitationMapper mapper;
    private final SecurityUtils securityUtils;
    private final AuditorAware<UUID> auditorAware;

    public GetMembershipSolicitation(MembershipSolicitationRepository solicitationRepo,
                                     MembershipSolicitationMapper mapper, SecurityUtils securityUtils,
                                     AuditorAware<UUID> auditorAware) {
        this.solicitationRepo = solicitationRepo;
        this.mapper = mapper;
        this.securityUtils = securityUtils;
        this.auditorAware = auditorAware;
    }

    @Transactional(readOnly = true)
    public MembershipSolicitationRDTO byId(UUID id) {
        MembershipSolicitationEntity solicitation = solicitationRepo.findById(id)
                .orElseThrow(() -> NotFoundException.resource("MembershipSolicitation", id));
        boolean manager = securityUtils.getLoggedUserAuthorities()
                .contains(PermissionEnum.MEMBER_MANAGE.getCode());
        UUID callerId = auditorAware.getCurrentAuditor().orElse(null);
        if (!manager && !solicitation.getAccount().getId().equals(callerId)) {
            throw NotFoundException.resource("MembershipSolicitation", id);
        }
        return mapper.entityToRDTO(solicitation);
    }
}
