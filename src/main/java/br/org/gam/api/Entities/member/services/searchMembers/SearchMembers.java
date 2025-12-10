package br.org.gam.api.Entities.member.services.searchMembers;

import br.org.gam.api.Entities.member.services.getMember.GetMemberRDTO;
import br.org.gam.api.common.specification.SpecificationFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchMembers {
    Page<GetMemberRDTO> search(List<SpecificationFilter> filters, Pageable pageable);
}
