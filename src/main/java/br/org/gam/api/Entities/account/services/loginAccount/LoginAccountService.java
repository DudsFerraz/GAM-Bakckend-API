package br.org.gam.api.Entities.account.services.loginAccount;

import br.org.gam.api.common.config.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class LoginAccountService implements ILoginAccountService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public LoginAccountService(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @Override
    public LoginAccountResponseDTO loginAccount(LoginAccountDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.email().value(),
                        dto.password()
                )
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(dto.email().value());

        final String jwt = jwtService.generateToken(userDetails);

        return new LoginAccountResponseDTO(jwt);
    }
}
