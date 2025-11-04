package br.org.gam.api.Entities.account.services.searchAccounts.service;

import br.org.gam.api.Entities.account.services.getAccountById.dto.GetAccountByIdDTO;
import br.org.gam.api.common.specification.SpecificationFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ISearchAccountsService {

    public Page<GetAccountByIdDTO> getAccounts(List<SpecificationFilter> filters, Pageable pageable);
}
