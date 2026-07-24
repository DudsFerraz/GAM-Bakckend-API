package br.org.gam.api.event.application;

import br.org.gam.api.event.persistence.EventEntity;
import br.org.gam.api.rbac.permission.domain.PermissionEnum;
import br.org.gam.api.security.SecurityUtils;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component("eventSecurity")
public class EventSecurity {
    private final SecurityUtils securityUtils;
    public EventSecurity(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    public boolean canGetEvent(EventEntity eventEntity) {
        if(eventEntity.getRequiredPermission() == null) return true;

        var permission = eventEntity.getRequiredPermission();
        PermissionEnum currentPermission = PermissionEnum.fromCode(permission.getCode()).orElse(null);
        if (!permission.isSystemManaged()
                || currentPermission == null
                || !currentPermission.getLabel().equals(permission.getLabel())
                || !currentPermission.getDescription().equals(permission.getDescription())
                || (currentPermission != PermissionEnum.EVENT_GET_MEMBER
                && currentPermission != PermissionEnum.EVENT_GET_COORD)) {
            return false;
        }

        Set<String> userAuthorities = securityUtils.getLoggedUserAuthorities();

        return userAuthorities.contains(permission.getCode());
    }
}
