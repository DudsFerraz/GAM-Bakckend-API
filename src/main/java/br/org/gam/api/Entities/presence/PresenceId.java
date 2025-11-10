package br.org.gam.api.Entities.presence;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public record PresenceId(
        @NotNull UUID memberId,
        @NotNull UUID eventId
) implements Serializable {
    public PresenceId() {
        this(null, null);
    }

    public PresenceId(@NotNull UUID memberId, @NotNull UUID eventId) {
        this.memberId = memberId;
        this.eventId = eventId;
    }
}
