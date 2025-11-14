package br.org.gam.api.Entities.account.controller;

import br.org.gam.api.Entities.account.services.changePermissionLevel.IChangePermissionLevelService;
import br.org.gam.api.Entities.account.services.registerAccount.RegisterAccountDTO;
import br.org.gam.api.Entities.account.services.registerAccount.RegisterAccountResponseDTO;
import br.org.gam.api.Entities.account.services.getAccountById.GetAccountByIdDTO;
import br.org.gam.api.Entities.account.services.registerAccount.IRegisterAccountService;
import br.org.gam.api.Entities.account.services.getAccountById.IGetAccountByIdService;
import br.org.gam.api.Entities.account.services.searchAccounts.ISearchAccountsService;
import br.org.gam.api.common.specification.SearchDTO;
import br.org.gam.api.common.specification.SpecificationFilter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
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

    private final IGetAccountByIdService getAccountByIdService;
    private final ISearchAccountsService searchAccountsService;
    private final SpecificationFilterConverter specificationFilterConverter;

    public AccountController(IGetAccountByIdService getAccountByIdService,
                             ISearchAccountsService searchAccountsService,
                             @Qualifier("accountSpecificationFilterConverter") SpecificationFilterConverter specificationFilterConverter) {

        this.getAccountByIdService = getAccountByIdService;
        this.searchAccountsService = searchAccountsService;
        this.specificationFilterConverter = specificationFilterConverter;
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetAccountByIdDTO> getAccountById(@PathVariable UUID id) {
        GetAccountByIdDTO dto = getAccountByIdService.getAccountById(id);
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

//    @PatchMapping("/{id}/setVisitor")
//    public ResponseEntity changePermissionLevelToVisitor(@PathVariable UUID id){
//
//        changePermissionLevelService.setToVisitor(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    @PatchMapping("/{id}/setMember")
//    public ResponseEntity changePermissionLevelToMember(@PathVariable UUID id){
//
//        changePermissionLevelService.setToMember(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    @PatchMapping("/{id}/setCoord")
//    public ResponseEntity changePermissionLevelToCoord(@PathVariable UUID id){
//
//        changePermissionLevelService.setToCoord(id);
//        return ResponseEntity.noContent().build();
//    }
}
