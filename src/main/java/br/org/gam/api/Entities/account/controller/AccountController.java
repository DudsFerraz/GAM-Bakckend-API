package br.org.gam.api.Entities.account.controller;

import br.org.gam.api.Entities.account.services.createAccount.dto.CreateAccountDTO;
import br.org.gam.api.Entities.account.services.createAccount.dto.CreateAccountResponseDTO;
import br.org.gam.api.Entities.account.services.getAccountById.dto.GetAccountByIdDTO;
import br.org.gam.api.Entities.account.services.createAccount.service.ICreateAccountService;
import br.org.gam.api.Entities.account.services.getAccountById.service.IGetAccountByIdService;
import br.org.gam.api.Entities.account.services.searchAccounts.service.ISearchAccountsService;
import br.org.gam.api.common.specification.SearchDTO;
import br.org.gam.api.common.specification.SpecificationFilter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final ICreateAccountService createAccountService;
    private final IGetAccountByIdService getAccountByIdService;
    private final ISearchAccountsService getAccountsService;
    private final SpecificationFilterConverter specificationFilterConverter;

    public AccountController(ICreateAccountService createAccountService, IGetAccountByIdService getAccountByIdService, ISearchAccountsService getAccountsService, SpecificationFilterConverter specificationFilterConverter) {
        this.createAccountService = createAccountService;
        this.getAccountByIdService = getAccountByIdService;
        this.getAccountsService = getAccountsService;
        this.specificationFilterConverter = specificationFilterConverter;
    }


    @PostMapping
    public ResponseEntity<CreateAccountResponseDTO> createAccount(@Valid @RequestBody CreateAccountDTO dto) {
        CreateAccountResponseDTO responseDTO = createAccountService.createAccount(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDTO.id())
                .toUri();

        return ResponseEntity.created(location).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetAccountByIdDTO> getAccount(@PathVariable UUID id) {
        GetAccountByIdDTO dto = getAccountByIdService.getAccountById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping()
    public ResponseEntity<Page<GetAccountByIdDTO>> getAllAccounts(Pageable pageable) {
        return ResponseEntity.ok(
                getAccountsService.getAccounts(List.of(), pageable)
        );
    }

    @PostMapping("/search")
    public ResponseEntity<Page<GetAccountByIdDTO>> searchAccounts(@RequestBody @Valid SearchDTO searchDTO,
                                                               Pageable pageable) {

        List<SpecificationFilter> filters = specificationFilterConverter.convert(searchDTO.filters());

        return ResponseEntity.ok(
                getAccountsService.getAccounts(filters, pageable)
        );
    }
}
