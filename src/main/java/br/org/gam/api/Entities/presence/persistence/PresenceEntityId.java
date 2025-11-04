package br.org.gam.api.Entities.presence.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

public class PresenceEntityId {
    @Embeddable
    @Getter
    @Setter
    @EqualsAndHashCode // Essencial para chaves compostas
    public class PresenceId implements Serializable {

        @Column(name = "member_id")
        private UUID memberId;

        @Column(name = "event_id")
        private UUID eventId;
    }
}
