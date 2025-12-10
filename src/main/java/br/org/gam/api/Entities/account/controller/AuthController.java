package br.org.gam.api.Entities.account.controller;

import br.org.gam.api.Entities.account.services.loginAccount.LoginAccount;
import br.org.gam.api.Entities.account.services.loginAccount.LoginAccountDTO;
import br.org.gam.api.Entities.account.services.loginAccount.LoginAccountRDTO;
import br.org.gam.api.Entities.account.services.registerAccount.RegisterAccount;
import br.org.gam.api.Entities.account.services.registerAccount.RegisterAccountDTO;
import br.org.gam.api.Entities.account.services.registerAccount.RegisterAccountRDTO;
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
    private final RegisterAccount registerAccountService;
    private final LoginAccount loginAccount;

    public AuthController(RegisterAccount registerAccountService, LoginAccount loginAccount) {
        this.registerAccountService = registerAccountService;
        this.loginAccount = loginAccount;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterAccountRDTO> createAccount(@Valid @RequestBody RegisterAccountDTO dto) {
        RegisterAccountRDTO responseDTO = registerAccountService.register(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDTO.id())
                .toUri();

        return ResponseEntity.created(location).body(responseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginAccountRDTO> login(@RequestBody @Valid LoginAccountDTO dto) {

        return ResponseEntity.ok(
                loginAccount.login(dto)
        );
    }

}
