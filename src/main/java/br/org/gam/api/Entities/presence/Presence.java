package br.org.gam.api.Entities.presence;

import br.org.gam.api.Entities.account.Account;
import br.org.gam.api.Entities.event.Event;
import br.org.gam.api.Entities.member.Member;
import br.org.gam.api.common.Name;
import br.org.gam.api.common.myPhoneNumber.MyPhoneNumber;

import java.time.LocalDate;
import java.util.Objects;

public class Presence {
    private PresenceId id;
    private Member member;
    private Event event;
    private String observations;

    /**
     * @deprecated <b>ESTE CONSTRUTOR É EXCLUSIVO PARA USO INTERNO (JPA/MapStruct).</b>
     * <br> <br>
     * <b> Use o método fábrica {@link #create(Member member, Event event, String observations)}.
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

    public static Presence create(Member member, Event event, String observations) {
        Objects.requireNonNull(member);
        Objects.requireNonNull(event);

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
