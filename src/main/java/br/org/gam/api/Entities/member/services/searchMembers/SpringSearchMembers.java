package br.org.gam.api.Entities.member.services.searchMembers;

import br.org.gam.api.Entities.member.MemberMapper;
import br.org.gam.api.Entities.member.persistence.MemberRepository;
import br.org.gam.api.Entities.member.persistence.MemberEntity;
import br.org.gam.api.Entities.member.services.getMember.GetMemberRDTO;
import br.org.gam.api.common.specification.SpecificationBuilder;
import br.org.gam.api.common.specification.SpecificationFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpringSearchMembers implements SearchMembers {

    private final MemberRepository memberRepo;
    private final MemberMapper memberMapper;

    public SpringSearchMembers(MemberRepository memberRepo, MemberMapper memberMapper) {
        this.memberRepo = memberRepo;
        this.memberMapper = memberMapper;
    }

    @Override
    public Page<GetMemberRDTO> search(List<SpecificationFilter> filters, Pageable pageable) {
        Specification<MemberEntity> spec = SpecificationBuilder.build(filters);

        Page<MemberEntity> entitiesPage = memberRepo.findAll(spec, pageable);

        return entitiesPage.map(memberMapper::fromEntityToGetMemberRDTO);
    }


}
