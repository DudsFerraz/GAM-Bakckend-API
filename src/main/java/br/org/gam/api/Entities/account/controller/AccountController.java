package br.org.gam.api.Entities.account.controller;

import br.org.gam.api.Entities.account.services.AccountRDTO;
import br.org.gam.api.Entities.account.services.getAccount.GetAccount;
import br.org.gam.api.Entities.account.services.searchAccounts.SearchAccounts;
import br.org.gam.api.common.specification.SearchDTO;
import br.org.gam.api.common.specification.SpecificationFilter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final GetAccount getAccount;
    private final SearchAccounts searchAccountsService;
    private final SpecificationFilterConverter specificationFilterConverter;

    public AccountController(GetAccount getAccount,
                             SearchAccounts searchAccountsService,
                             @Qualifier("accountSpecificationFilterConverter") SpecificationFilterConverter specificationFilterConverter) {

        this.getAccount = getAccount;
        this.searchAccountsService = searchAccountsService;
        this.specificationFilterConverter = specificationFilterConverter;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountRDTO> getAccountById(@PathVariable UUID id) {
        AccountRDTO dto = getAccount.byId(id);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasAuthority('ACCOUNT_GET_ALL')")
    @GetMapping()
    public ResponseEntity<Page<AccountRDTO>> getAllAccounts(Pageable pageable) {
        return ResponseEntity.ok(
                searchAccountsService.search(List.of(), pageable)
        );
    }

    @PreAuthorize("hasAuthority('ACCOUNT_GET_ALL')")
    @PostMapping("/search")
    public ResponseEntity<Page<AccountRDTO>> searchAccounts(@RequestBody @Valid SearchDTO searchDTO,
                                                            Pageable pageable) {

        List<SpecificationFilter> filters = specificationFilterConverter.convert(searchDTO.filters());

        return ResponseEntity.ok(
                searchAccountsService.search(filters, pageable)
        );
    }
}
