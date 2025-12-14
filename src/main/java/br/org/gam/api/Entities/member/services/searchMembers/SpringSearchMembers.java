package br.org.gam.api.Entities.member.services.searchMembers;

import br.org.gam.api.Entities.account.services.getAccount.GetAccount;
import br.org.gam.api.Entities.account.services.getAccount.GetAccountRDTO;
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
    private final GetAccount getAccount;

    public SpringSearchMembers(MemberRepository memberRepo, MemberMapper memberMapper, GetAccount getAccount) {
        this.memberRepo = memberRepo;
        this.memberMapper = memberMapper;
        this.getAccount = getAccount;
    }

    @Override
    public Page<GetMemberRDTO> search(List<SpecificationFilter> filters, Pageable pageable) {
        Specification<MemberEntity> spec = SpecificationBuilder.build(filters);

        Page<MemberEntity> entitiesPage = memberRepo.findAll(spec, pageable);

        return entitiesPage
                .map(entity -> {
                    int age = memberMapper.fromEntityToDomain(entity).getAge();
                    GetAccountRDTO accountRDTO = getAccount.byId(entity.getAccount().getId());
                    return memberMapper.fromEntityToGetMemberRDTO(entity, age, accountRDTO);
                });
    }


}
