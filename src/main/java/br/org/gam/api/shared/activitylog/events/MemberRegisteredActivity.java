package br.org.gam.api.shared.activitylog.events;

import java.util.UUID;

public record MemberRegisteredActivity(
        UUID memberId,
        UUID accountId,
        UUID roleAddedId,
        UUID roleRemovedId,
        String reason
) {
}
