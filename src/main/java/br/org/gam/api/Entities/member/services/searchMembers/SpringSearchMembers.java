package br.org.gam.api.Entities.member.services.searchMembers;

import br.org.gam.api.Entities.member.MemberMapper;
import br.org.gam.api.Entities.member.persistence.MemberRepository;
import br.org.gam.api.Entities.member.persistence.MemberEntity;
import br.org.gam.api.Entities.member.persistence.MemberSpecifications;
import br.org.gam.api.Entities.member.services.MemberRDTO;
import br.org.gam.api.common.security.SecurityUtils;
import br.org.gam.api.Entities.member.security.MemberSecuritySpecification;
import br.org.gam.api.common.specification.SpecificationBuilder;
import br.org.gam.api.common.specification.SpecificationFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SpringSearchMembers implements SearchMembers {

    private final MemberRepository memberRepo;
    private final MemberMapper memberMapper;
    private final SecurityUtils securityUtils;

    public SpringSearchMembers(MemberRepository memberRepo, MemberMapper memberMapper, SecurityUtils securityUtils) {
        this.memberRepo = memberRepo;
        this.memberMapper = memberMapper;
        this.securityUtils = securityUtils;
    }

    @Override
    public Page<MemberRDTO> search(List<SpecificationFilter> filters, Pageable pageable) {
        Set<String> authorities = securityUtils.getLoggedUserAuthorities();
        Specification<MemberEntity> securityFilter = MemberSecuritySpecification.canGetMember(authorities);
        Specification<MemberEntity> searchFilters = SpecificationBuilder.build(filters);
        Specification<MemberEntity> spec = securityFilter.and(searchFilters).and(MemberSpecifications.fetchAccount());

        return memberRepo.findAll(spec, pageable)
                .map(memberMapper::entityToMemberRDTO);
    }


}
