package br.org.gam.api.account.application;

import br.org.gam.api.account.persistence.AccountEntity;
import br.org.gam.api.rbac.permission.domain.PermissionEnum;
import br.org.gam.api.security.application.AccountDetails;
import br.org.gam.api.shared.domain.GamEmail;
import br.org.gam.api.testing.annotation.StructuralTest;
import br.org.gam.api.testing.annotation.UnitTest;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.assertThat;

@UnitTest
@StructuralTest
@DisplayName("Account Security")
class AccountSecurityTest {

    private final AccountSecurity accountSecurity = new AccountSecurity();

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("no authentication -> cannot retrieve account")
    void noAuthenticationCannotRetrieveAccount() {
        assertThat(accountSecurity.canGetAccount(UUID.randomUUID())).isFalse();
    }

    @Test
    @DisplayName("unauthenticated AccountDetails principal -> cannot retrieve account")
    void unauthenticatedAccountDetailsPrincipalCannotRetrieveAccount() {
        AccountDetails caller = accountDetails(UUID.randomUUID(), List.of());
        Authentication authentication = new UsernamePasswordAuthenticationToken(caller, "credentials");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        assertThat(accountSecurity.canGetAccount(caller.getId())).isFalse();
    }

    @Test
    @DisplayName("authenticated non-AccountDetails principal -> cannot retrieve account")
    void nonAccountDetailsPrincipalCannotRetrieveAccount() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "not-an-account",
                "credentials",
                List.of(new SimpleGrantedAuthority(PermissionEnum.ACCOUNT_GET.getCode()))
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        assertThat(accountSecurity.canGetAccount(UUID.randomUUID())).isFalse();
    }

    @Test
    @DisplayName("ACCOUNT_GET authority -> retrieves another account")
    void accountGetAuthorityCanRetrieveAnotherAccount() {
        AccountDetails caller = accountDetails(
                UUID.randomUUID(),
                List.of(new SimpleGrantedAuthority(PermissionEnum.ACCOUNT_GET.getCode()))
        );
        SecurityContextHolder.getContext().setAuthentication(authenticated(caller));

        assertThat(accountSecurity.canGetAccount(UUID.randomUUID())).isTrue();
    }

    @Test
    @DisplayName("authenticated caller without ACCOUNT_GET -> retrieves only itself")
    void callerWithoutAccountGetCanRetrieveOnlyItself() {
        AccountDetails caller = accountDetails(UUID.randomUUID(), List.of());
        SecurityContextHolder.getContext().setAuthentication(authenticated(caller));

        assertThat(accountSecurity.canGetAccount(caller.getId())).isTrue();
        assertThat(accountSecurity.canGetAccount(UUID.randomUUID())).isFalse();
    }

    private static Authentication authenticated(AccountDetails accountDetails) {
        return new UsernamePasswordAuthenticationToken(
                accountDetails,
                "credentials",
                accountDetails.getAuthorities()
        );
    }

    private static AccountDetails accountDetails(UUID accountId, List<SimpleGrantedAuthority> authorities) {
        AccountEntity account = new AccountEntity();
        account.setId(accountId);
        account.setEmail(GamEmail.of("security-" + accountId + "@example.com"));
        account.setPasswordHash("hash");
        return new AccountDetails(account, authorities);
    }
}
