package br.org.gam.api.Entities.account.controller;

import br.org.gam.api.Entities.account.services.getAccountById.GetAccountByIdDTO;
import br.org.gam.api.Entities.account.services.getAccountById.GetAccountById;
import br.org.gam.api.Entities.account.services.searchAccounts.SearchAccounts;
import br.org.gam.api.common.specification.SearchDTO;
import br.org.gam.api.common.specification.SpecificationFilter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final GetAccountById getAccountById;
    private final SearchAccounts searchAccountsService;
    private final SpecificationFilterConverter specificationFilterConverter;

    public AccountController(GetAccountById getAccountById,
                             SearchAccounts searchAccountsService,
                             @Qualifier("accountSpecificationFilterConverter") SpecificationFilterConverter specificationFilterConverter) {

        this.getAccountById = getAccountById;
        this.searchAccountsService = searchAccountsService;
        this.specificationFilterConverter = specificationFilterConverter;
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetAccountByIdDTO> getAccountById(@PathVariable UUID id) {
        GetAccountByIdDTO dto = getAccountById.getAccountById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping()
    public ResponseEntity<Page<GetAccountByIdDTO>> getAllAccounts(Pageable pageable) {
        return ResponseEntity.ok(
                searchAccountsService.searchAccounts(List.of(), pageable)
        );
    }

    @PostMapping("/search")
    public ResponseEntity<Page<GetAccountByIdDTO>> searchAccounts(@RequestBody @Valid SearchDTO searchDTO,
                                                               Pageable pageable) {

        List<SpecificationFilter> filters = specificationFilterConverter.convert(searchDTO.filters());

        return ResponseEntity.ok(
                searchAccountsService.searchAccounts(filters, pageable)
        );
    }

//    @GetMapping
//    public ResponseEntity<>
}
