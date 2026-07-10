package br.org.gam.api.account.application;

import br.org.gam.api.rbac.permission.domain.PermissionEnum;
import br.org.gam.api.security.application.AccountDetails;
import java.util.UUID;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("accountSecurity")
public class AccountSecurity {

    public boolean canGetAccount(UUID targetAccountId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
                || !(authentication.getPrincipal() instanceof AccountDetails accountDetails)) {
            return false;
        }

        boolean canGetAnyAccount = authentication.getAuthorities().stream()
                .anyMatch(authority -> PermissionEnum.ACCOUNT_GET.getCode().equals(authority.getAuthority()));

        return canGetAnyAccount || accountDetails.getId().equals(targetAccountId);
    }
}
