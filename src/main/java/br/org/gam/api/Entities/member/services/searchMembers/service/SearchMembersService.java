package br.org.gam.api.Entities.member.services.searchMembers.service;

import br.org.gam.api.Entities.account.persistence.AccountEntity;
import br.org.gam.api.Entities.member.common.IMemberMapper;
import br.org.gam.api.Entities.member.persistence.IMemberRepository;
import br.org.gam.api.Entities.member.persistence.MemberEntity;
import br.org.gam.api.Entities.member.services.getMemberById.dto.GetMemberByIdDTO;
import br.org.gam.api.common.specification.SpecificationBuilder;
import br.org.gam.api.common.specification.SpecificationFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class SearchMembersService implements ISearchMembersService {

    private final IMemberRepository memberRepo;
    private final IMemberMapper memberMapper;

    public SearchMembersService(IMemberRepository memberRepo, IMemberMapper memberMapper) {
        this.memberRepo = memberRepo;
        this.memberMapper = memberMapper;
    }

    @Override
    public Page<GetMemberByIdDTO> searchMembers(List<SpecificationFilter> filters, Pageable pageable) {
        Specification<MemberEntity> spec = SpecificationBuilder.build(filters);

        Page<MemberEntity> entitiesPage = memberRepo.findAll(spec, pageable);

        return entitiesPage.map(memberMapper::fromEntityToGetMemberByIdDTO);
    }
}
