package br.org.gam.api.member.solicitation.application.useCases;

import br.org.gam.api.member.solicitation.application.MembershipSolicitationMapper;
import br.org.gam.api.member.solicitation.application.MembershipSolicitationRDTO;
import br.org.gam.api.member.solicitation.application.search.MembershipSolicitationSearchFilterConverter;
import br.org.gam.api.member.solicitation.persistence.MembershipSolicitationEntity;
import br.org.gam.api.member.solicitation.persistence.MembershipSolicitationRepository;
import br.org.gam.api.member.solicitation.persistence.MembershipSolicitationSpecifications;
import br.org.gam.api.rbac.permission.domain.PermissionEnum;
import br.org.gam.api.security.SecurityUtils;
import br.org.gam.api.shared.specification.SearchDTO;
import java.util.UUID;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SearchMembershipSolicitations {
    private final MembershipSolicitationRepository solicitationRepo;
    private final MembershipSolicitationMapper mapper;
    private final MembershipSolicitationSearchFilterConverter filterConverter;
    private final SecurityUtils securityUtils;
    private final AuditorAware<UUID> auditorAware;

    public SearchMembershipSolicitations(MembershipSolicitationRepository solicitationRepo,
                                         MembershipSolicitationMapper mapper,
                                         MembershipSolicitationSearchFilterConverter filterConverter,
                                         SecurityUtils securityUtils, AuditorAware<UUID> auditorAware) {
        this.solicitationRepo = solicitationRepo;
        this.mapper = mapper;
        this.filterConverter = filterConverter;
        this.securityUtils = securityUtils;
        this.auditorAware = auditorAware;
    }

    @Transactional(readOnly = true)
    public Page<MembershipSolicitationRDTO> search(SearchDTO searchDTO, Pageable pageable) {
        Specification<MembershipSolicitationEntity> specification = filterConverter.convert(searchDTO);
        boolean manager = securityUtils.getLoggedUserAuthorities()
                .contains(PermissionEnum.MEMBER_MANAGE.getCode());
        if (!manager) {
            UUID accountId = auditorAware.getCurrentAuditor()
                    .orElseThrow(() -> new IllegalStateException("Authenticated Account is required."));
            specification = specification.and(MembershipSolicitationSpecifications.belongsToAccount(accountId));
        }
        specification = specification.and(MembershipSolicitationSpecifications.fetchResponseRelations());
        return solicitationRepo.findAll(specification, pageable).map(mapper::entityToRDTO);
    }
}
