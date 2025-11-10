package br.org.gam.api.Entities.member.services.searchMembers;

import br.org.gam.api.Entities.member.services.getMemberById.GetMemberByIdDTO;
import br.org.gam.api.common.specification.SpecificationFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ISearchMembersService {
    Page<GetMemberByIdDTO> searchMembers(List<SpecificationFilter> filters, Pageable pageable);
}
