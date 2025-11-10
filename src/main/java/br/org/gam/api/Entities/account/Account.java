package br.org.gam.api.Entities.account;

import br.org.gam.api.common.PermissionLevelEnum;

import java.util.Objects;
import java.util.UUID;

public class Account {
    private UUID id;

    private Email email;

    private String passwordHash;

    private String displayName;

    private PermissionLevelEnum permissionLevel;

    /**
     * @deprecated <b>ESTE CONSTRUTOR É EXCLUSIVO PARA USO INTERNO (JPA/MapStruct).</b>
     * <br> <br>
     * <b> Use o método fábrica {@link #create(Email email, String passwordHash, String displayName)}.
     */
    @Deprecated
    Account(UUID id, Email email, String passwordHash, String displayName, PermissionLevelEnum permissionLevel) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.displayName = displayName;
        this.permissionLevel = permissionLevel;
    }

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


        PermissionLevelEnum permissionLevel = PermissionLevelEnum.VISITOR;
        String cleanDisplayName = displayName.trim();

        return new Account(email, passwordHash, cleanDisplayName, permissionLevel);
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
