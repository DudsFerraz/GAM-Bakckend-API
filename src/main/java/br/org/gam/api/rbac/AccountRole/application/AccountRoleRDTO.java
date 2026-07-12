package br.org.gam.api.rbac.accountRole.application;

import br.org.gam.api.account.application.AccountRDTO;
import br.org.gam.api.rbac.role.application.RoleRDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.UUID;

public record AccountRoleRDTO(
        @JsonIgnore UUID assignmentId,
        AccountRDTO account,
        RoleRDTO role
) {
    public AccountRoleRDTO(AccountRDTO account, RoleRDTO role) {
        this(null, account, role);
    }
}
