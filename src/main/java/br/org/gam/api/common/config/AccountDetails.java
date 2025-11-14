package br.org.gam.api.common.config;

import br.org.gam.api.Entities.account.persistence.AccountEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
public class AccountDetails implements UserDetails {

    private final UUID id;
    private final String email; // É o "username"
    private final String password;
    // Futuramente, aqui entrarão 'Roles' e 'Permissions'
    // private final Collection<? extends GrantedAuthority> authorities;

    public AccountDetails(AccountEntity account) {
        this.id = account.getId();
        this.email = account.getEmail().value();
        this.password = account.getPasswordHash();
        // Por enquanto, sem permissões
        // this.authorities = ...
    }

    public UUID getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO: Mapear Roles/Permissions para GrantedAuthority
        return List.of(); // Por enquanto, vazio
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    // tudo como 'true' por enquanto
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
