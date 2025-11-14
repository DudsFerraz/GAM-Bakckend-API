package br.org.gam.api.Entities.account.controller;

import br.org.gam.api.Entities.account.services.loginAccount.ILoginAccountService;
import br.org.gam.api.Entities.account.services.loginAccount.LoginAccountDTO;
import br.org.gam.api.Entities.account.services.loginAccount.LoginAccountResponseDTO;
import br.org.gam.api.Entities.account.services.registerAccount.IRegisterAccountService;
import br.org.gam.api.Entities.account.services.registerAccount.RegisterAccountDTO;
import br.org.gam.api.Entities.account.services.registerAccount.RegisterAccountResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final IRegisterAccountService registerAccountService;
    private final ILoginAccountService loginAccountService;

    public AuthController(IRegisterAccountService registerAccountService, ILoginAccountService loginAccountService) {
        this.registerAccountService = registerAccountService;
        this.loginAccountService = loginAccountService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterAccountResponseDTO> createAccount(@Valid @RequestBody RegisterAccountDTO dto) {
        RegisterAccountResponseDTO responseDTO = registerAccountService.registerAccount(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDTO.id())
                .toUri();

        return ResponseEntity.created(location).body(responseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginAccountResponseDTO> login(@RequestBody @Valid LoginAccountDTO dto) {

        return ResponseEntity.ok(
                loginAccountService.loginAccount(dto)
        );
    }

}
