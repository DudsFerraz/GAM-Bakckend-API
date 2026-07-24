package br.org.gam.api.shared.activitylog.events;

import br.org.gam.api.shared.activitylog.ActivityAction;
import java.util.Map;
import java.util.UUID;

public record EventChangedActivity(
        ActivityAction action,
        UUID eventId,
        String reason,
        String summary,
        Map<String, Object> metadata
) {
}
