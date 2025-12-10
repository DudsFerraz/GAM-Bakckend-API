package br.org.gam.api.Entities.account.services.loginAccount;

import br.org.gam.api.common.config.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class SpringLoginAccount implements LoginAccount {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public SpringLoginAccount(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @Override
    public LoginAccountRDTO login(LoginAccountDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.email().value(),
                        dto.password()
                )
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(dto.email().value());

        final String jwt = jwtService.generateToken(userDetails);

        return new LoginAccountRDTO(jwt);
    }
}
