package br.org.gam.api.Entities.presence;

import br.org.gam.api.Entities.event.Event;
import br.org.gam.api.Entities.member.Member;

import java.util.Objects;

public class Presence {
    private PresenceId id;
    private Member member;
    private Event event;
    private String observations;

    /**
     * @deprecated <b>ESTE CONSTRUTOR É EXCLUSIVO PARA USO INTERNO (JPA/MapStruct).</b>
     * <br> <br>
     * <b> Use o método fábrica {@link #register(Member member, Event event, String observations)}.
     */
    @Deprecated
    Presence(PresenceId id, Member member, Event event, String observations) {
        this.id = id;
        this.member = member;
        this.event = event;
        this.observations = observations;
    }

    private Presence(Member member, Event event, String observations) {
        this.member = member;
        this.event = event;
        this.observations = observations;
    }

    public static Presence register(Member member, Event event, String observations) {
        Objects.requireNonNull(member, "Present member must not be null");
        Objects.requireNonNull(event, "Presence event must not be null");
        if (observations != null || observations.isBlank()) {
            observations = observations.trim();
        }

        return new Presence(member, event, observations);
    }


    public Member getMember() {
        return member;
    }

    public Event getEvent() {
        return event;
    }

    public String getObservations() {
        return observations;
    }
}
