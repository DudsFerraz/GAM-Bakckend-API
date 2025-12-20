package br.org.gam.api.common.auth;

import br.org.gam.api.Entities.RBAC.accountRole.persistence.AccountRoleEntity;
import br.org.gam.api.Entities.RBAC.permission.persistence.PermissionEntity;
import br.org.gam.api.Entities.RBAC.role.persistence.RoleEntity;
import br.org.gam.api.Entities.RBAC.rolePermission.persistence.RolePermissionEntity;
import br.org.gam.api.Entities.account.myEmail.MyEmail;
import br.org.gam.api.Entities.account.persistence.AccountEntity;
import br.org.gam.api.Entities.account.persistence.AccountRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class AccountDetailsService implements UserDetailsService {

    private final AccountRepository accountRepo;

    public AccountDetailsService(@Lazy AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String subject) throws UsernameNotFoundException {

        AccountEntity accountEntity;
        try {
            UUID id = UUID.fromString(subject);
            accountEntity = accountRepo.findById(id)
                    .orElseThrow(() -> new UsernameNotFoundException("User ID not found: " + subject));
        } catch (IllegalArgumentException e) {
            accountEntity = accountRepo.findByEmail(MyEmail.of(subject))
                    .orElseThrow(() -> new UsernameNotFoundException("User Email not found: " + subject));
        }

        Set<GrantedAuthority> authorities = new HashSet<>();

        if (accountEntity.getAccountRoles() != null) {
            for (AccountRoleEntity accountRole : accountEntity.getAccountRoles()) {

                RoleEntity role = accountRole.getRole();
                if (role == null) continue;

                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase()));

                if (role.getRolePermissions() != null) {
                    for (RolePermissionEntity rolePermission : role.getRolePermissions()) {
                        PermissionEntity permission = rolePermission.getPermission();

                        if (permission != null) {
                            authorities.add(new SimpleGrantedAuthority(permission.getName().toUpperCase()));
                        }
                    }
                }
            }
        }

        return new AccountDetails(accountEntity, authorities);
    }
}
