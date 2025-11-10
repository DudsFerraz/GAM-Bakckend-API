package br.org.gam.api.Entities.presence.persistence;

import br.org.gam.api.Entities.event.persistence.EventEntity;
import br.org.gam.api.Entities.member.persistence.MemberEntity;
import br.org.gam.api.Entities.presence.PresenceId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Setter
@Entity
@Getter
@NoArgsConstructor
@Table(name = "presences")
public class PresenceEntity {

    @EmbeddedId
    private PresenceId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("memberId")
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("eventId")
    @JoinColumn(name = "event_id")
    private EventEntity event;

    @Column(name = "observations")
    private String observations;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}
