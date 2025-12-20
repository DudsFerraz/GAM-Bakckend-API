package br.org.gam.api.Entities.account.services.loginAccount;

import br.org.gam.api.common.auth.TokensDTO;

public interface LoginAccount {
    TokensDTO login(LoginAccountDTO dto);
}
