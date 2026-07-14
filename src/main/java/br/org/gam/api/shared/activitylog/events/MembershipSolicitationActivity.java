package br.org.gam.api.shared.activitylog.events;

import br.org.gam.api.shared.activitylog.ActivityAction;
import java.util.UUID;

public record MembershipSolicitationActivity(
        ActivityAction action,
        UUID solicitationId,
        UUID applicantAccountId,
        String previousStatus,
        String newStatus,
        UUID memberId,
        UUID roleAddedId,
        UUID roleRemovedId,
        String reason
) {
}
