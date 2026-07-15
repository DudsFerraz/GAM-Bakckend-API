package br.org.gam.api.account.web;

import br.org.gam.api.account.application.AccountRDTO;
import br.org.gam.api.account.application.useCases.GetAccount;
import br.org.gam.api.account.application.useCases.SearchAccounts;
import br.org.gam.api.rbac.permission.domain.PermissionEnum;
import br.org.gam.api.shared.specification.SearchDTO;
import br.org.gam.api.shared.web.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final GetAccount getAccount;
    private final SearchAccounts searchAccountsService;

    public AccountController(GetAccount getAccount, SearchAccounts searchAccountsService) {

        this.getAccount = getAccount;
        this.searchAccountsService = searchAccountsService;
    }

    @PreAuthorize("@accountSecurity.canGetAccount(#id)")
    @Operation(operationId = "getAccount")
    @GetMapping("/{id}")
    public ResponseEntity<AccountRDTO> getAccountById(@PathVariable UUID id) {
        AccountRDTO dto = getAccount.byId(id);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasAuthority('" + PermissionEnum.Code.ACCOUNT_SEARCH + "')")
    @Operation(operationId = "searchAccounts")
    @PostMapping("/search")
    public ResponseEntity<PagedResponse<AccountRDTO>> searchAccounts(@RequestBody @Valid SearchDTO searchDTO,
                                                                       Pageable pageable) {

        return ResponseEntity.ok(PagedResponse.from(searchAccountsService.search(searchDTO, pageable)));
    }
}
