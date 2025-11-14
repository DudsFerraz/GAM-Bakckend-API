package br.org.gam.api.common.config;

import br.org.gam.api.Entities.account.MyEmail;
import br.org.gam.api.Entities.account.persistence.IAccountRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class AccountUserDetailsService implements UserDetailsService {

    private final IAccountRepository accountRepo;

    public AccountUserDetailsService(@Lazy IAccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 'username' é o email
        return accountRepo.findByEmail(MyEmail.of(username))
                .map(AccountDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
