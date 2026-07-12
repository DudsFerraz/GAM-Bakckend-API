package br.org.gam.api.rbac.accountRole.web;

import br.org.gam.api.rbac.accountRole.application.AccountRoleDTO;
import br.org.gam.api.rbac.accountRole.application.AccountRoleRDTO;
import br.org.gam.api.rbac.accountRole.application.AccountRolesRDTO;
import br.org.gam.api.rbac.accountRole.application.useCases.AddAccountRole;
import br.org.gam.api.rbac.accountRole.application.useCases.AddAccountRoleDTO;
import br.org.gam.api.rbac.accountRole.application.useCases.DropAccountRole;
import br.org.gam.api.rbac.accountRole.application.useCases.DropAccountRoleDTO;
import br.org.gam.api.rbac.accountRole.application.useCases.GetAccountRoles;
import br.org.gam.api.rbac.permission.domain.PermissionEnum;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/accounts/{accountId}/roles")
public class AccountRoleController {
    private final GetAccountRoles getAccountRoles;
    private final AddAccountRole addAccountRole;
    private final DropAccountRole dropAccountRole;

    public AccountRoleController(GetAccountRoles getAccountRoles, AddAccountRole addAccountRole,
                                 DropAccountRole dropAccountRole) {
        this.getAccountRoles = getAccountRoles;
        this.addAccountRole = addAccountRole;
        this.dropAccountRole = dropAccountRole;
    }

    @PreAuthorize("hasAuthority('" + PermissionEnum.Code.ACCOUNT_GET + "')")
    @GetMapping
    public ResponseEntity<AccountRolesRDTO> getRoles(@PathVariable UUID accountId) {
        return ResponseEntity.ok(getAccountRoles.get(accountId));
    }

    @PreAuthorize("hasAuthority('" + PermissionEnum.Code.ACCOUNT_ROLE_MANAGE + "')")
    @PostMapping
    public ResponseEntity<AccountRoleRDTO> addRole(@PathVariable UUID accountId,
                                                   @RequestBody @Valid AddAccountRoleDTO request) {
        AccountRoleRDTO response = addAccountRole.byDTO(
                new AccountRoleDTO(accountId, request.roleId(), request.reason())
        );
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{assignmentId}")
                .buildAndExpand(response.assignmentId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PreAuthorize("hasAuthority('" + PermissionEnum.Code.ACCOUNT_ROLE_MANAGE + "')")
    @PatchMapping("/{roleId}/drop")
    public ResponseEntity<Void> dropRole(@PathVariable UUID accountId, @PathVariable UUID roleId,
                                         @RequestBody @Valid DropAccountRoleDTO request) {
        dropAccountRole.byDTO(new AccountRoleDTO(accountId, roleId, request.reason()));
        return ResponseEntity.noContent().build();
    }
}
