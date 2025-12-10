package br.org.gam.api.Entities.account.services.searchAccounts;

import br.org.gam.api.Entities.account.services.getAccount.GetAccountRDTO;
import br.org.gam.api.common.specification.SpecificationFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface SearchAccounts {

    public Page<GetAccountRDTO> search(List<SpecificationFilter> filters, Pageable pageable);
}
