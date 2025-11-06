package br.org.gam.api.Entities.account.domain;

import br.org.gam.api.Entities.account.common.Email;
import br.org.gam.api.Entities.account.common.PermissionLevelEnum;

import java.util.Objects;
import java.util.UUID;

public class Account {
    private UUID id;

    private Email email;

    private String passwordHash;

    private String displayName;

    private PermissionLevelEnum permissionLevel;

    private Account(Email email, String passwordHash, String displayName, PermissionLevelEnum permissionLevel) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.displayName = displayName;
        this.permissionLevel = permissionLevel;
    }

    public static Account create(Email email, String passwordHash, String displayName) {
        Objects.requireNonNull(email, "Email cannot be null.");
        Objects.requireNonNull(passwordHash, "Password hash cannot be null.");
        Objects.requireNonNull(displayName, "Display name cannot be null.");
        if (passwordHash.isBlank()) throw new IllegalArgumentException("Password hash cannot be blank.");
        if (displayName.isBlank()) throw new IllegalArgumentException("Display name cannot be blank.");


        PermissionLevelEnum permissionLevelEnum = PermissionLevelEnum.MEMBER;

        return new Account(email, passwordHash, displayName, permissionLevelEnum);
    }

    public void setPermissionLevelToMember() {
        this.permissionLevel = PermissionLevelEnum.MEMBER;
    }

    public void setPermissionLevelToCoord() {
        this.permissionLevel = PermissionLevelEnum.COORDINATOR;
    }

    public void setPermissionLevelToVisitor() {
        this.permissionLevel = PermissionLevelEnum.VISITOR;
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
