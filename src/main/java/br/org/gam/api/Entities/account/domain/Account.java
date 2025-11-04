package br.org.gam.api.Entities.account.domain;

import br.org.gam.api.Entities.account.common.Email;
import br.org.gam.api.Entities.account.common.PermissionLevelEnum;

import java.util.UUID;

public class Account {
    private UUID id;

    private Email email;

    private String passwordHash;

    private String displayName;

    private PermissionLevelEnum permissionLevel;

    public Account(Email email, String passwordHash, String displayName) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.displayName = displayName;
        this.permissionLevel = PermissionLevelEnum.MEMBER;
    }




    public UUID getId() {
        return id;
    }

    public Email getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public PermissionLevelEnum getPermissionLevel() {
        return permissionLevel;
    }

    public String getDisplayName() {
        return displayName;
    }
}
