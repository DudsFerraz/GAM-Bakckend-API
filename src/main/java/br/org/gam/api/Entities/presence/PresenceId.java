package br.org.gam.api.Entities.presence;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class PresenceId implements Serializable {

    @Column(name = "member_id")
    private UUID memberId;

    @Column(name = "event_id")
    private UUID eventId;

}
