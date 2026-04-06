package br.org.gam.api.Entities.events.generic.security;

import br.org.gam.api.Entities.events.generic.persistence.EventEntity;
import br.org.gam.api.common.security.SecurityUtils;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component("eventSecurity")
public class EventSecurity {
    private final SecurityUtils securityUtils;
    public EventSecurity(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    public boolean canGetEvent(EventEntity eventEntity) {
        if(eventEntity.getRequiredPermission() == null) return true;

        Set<String> userAuthorities = securityUtils.getLoggedUserAuthorities();

        return userAuthorities.contains(eventEntity.getRequiredPermission().getName());
    }
}
