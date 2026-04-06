package br.org.gam.api.Entities.events.core.security;

import br.org.gam.api.Entities.events.core.persistence.EventEntity;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;

public class EventSecuritySpecification {
    public static Specification<EventEntity> canGetEvent(Set<String> userAuthorities) {
        return (root, query, cb) -> {
            var isPublic = cb.isNull(root.get("requiredPermission"));
            if (userAuthorities == null || userAuthorities.isEmpty()) {
                return isPublic;
            }

            var permissionJoin = root.join("requiredPermission", JoinType.LEFT);
            var hasAuthority = permissionJoin.get("name").in(userAuthorities);

            return cb.or(isPublic, hasAuthority);
        };
    }
}
