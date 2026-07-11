package br.org.gam.api.account.domain;

import br.org.gam.api.shared.domain.GamEmail;
import br.org.gam.api.shared.persistence.UUIDGenerator;
import java.util.Objects;
import java.util.UUID;

public class Account {
    private UUID id;
    private GamEmail email;
    private String passwordHash;
    private String displayName;

    /**
     * @deprecated <b>ESTE CONSTRUTOR É EXCLUSIVO PARA USO INTERNO E JPA/MapStruct.</b>
     * <br> <br>
     * <b> Use o método fábrica {@link #register(GamEmail email, String passwordHash, String displayName)}.
     */
    @Deprecated
    public Account(UUID id, GamEmail email, String passwordHash, String displayName) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.displayName = displayName;
    }

    public static Account register(GamEmail email, String passwordHash, String displayName) {
        Objects.requireNonNull(email, "Email cannot be null.");
        Objects.requireNonNull(passwordHash, "Password hash cannot be null.");
        Objects.requireNonNull(displayName, "Display name cannot be null.");
        if (passwordHash.isBlank()) throw new IllegalArgumentException("Password hash cannot be blank.");

        displayName = displayName.trim();
        if (displayName.isBlank()) throw new IllegalArgumentException("Display name cannot be blank.");
        if (displayName.length() > 50) {
            throw new IllegalArgumentException("Display name cannot exceed 50 characters.");
        }

        UUID id = UUIDGenerator.generateUUIDV7();

        return new Account(id, email, passwordHash, displayName);
    }

    public UUID getId() {
        return id;
    }

    public GamEmail getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getDisplayName() {
        return displayName;
    }
}
