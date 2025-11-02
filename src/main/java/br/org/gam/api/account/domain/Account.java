package br.org.gam.api.account.domain;

import br.org.gam.api.account.Email;
import br.org.gam.api.account.PermissionLevelEnum;

import java.util.UUID;

public class Account {
    private UUID id;

    private Email email;

    private String password;

    private PermissionLevelEnum permissionLevel;

}
