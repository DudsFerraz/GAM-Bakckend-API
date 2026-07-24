package br.org.gam.api.shared.activitylog.events;

import java.util.UUID;

public record PresenceRegisteredActivity(
        UUID presenceId,
        UUID memberId,
        UUID eventId,
        String observations,
        boolean observationsIncluded
) {
    public PresenceRegisteredActivity(UUID presenceId, UUID memberId, UUID eventId, String observations) {
        this(presenceId, memberId, eventId, observations, true);
    }

    public PresenceRegisteredActivity(UUID presenceId, UUID memberId, UUID eventId) {
        this(presenceId, memberId, eventId, null, false);
    }
}
