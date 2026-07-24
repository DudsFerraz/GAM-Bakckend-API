package br.org.gam.api.event.persistence;

import br.org.gam.api.rbac.permission.domain.PermissionEnum;
import jakarta.persistence.criteria.JoinType;
import java.util.Set;
import org.springframework.data.jpa.domain.Specification;

public class EventSecuritySpecification {
    public static Specification<EventEntity> canGetEvent(Set<String> userAuthorities) {
        return (root, query, cb) -> {
            var isPublic = cb.isNull(root.get("requiredPermission"));
            if (userAuthorities == null || userAuthorities.isEmpty()) {
                return isPublic;
            }

            var permissionJoin = root.join("requiredPermission", JoinType.LEFT);
            var memberAudienceIsCurrent = cb.and(
                    cb.equal(permissionJoin.get("code"), PermissionEnum.EVENT_GET_MEMBER.getCode()),
                    cb.equal(permissionJoin.get("label"), PermissionEnum.EVENT_GET_MEMBER.getLabel()),
                    cb.equal(permissionJoin.get("description"), PermissionEnum.EVENT_GET_MEMBER.getDescription())
            );
            var coordinatorAudienceIsCurrent = cb.and(
                    cb.equal(permissionJoin.get("code"), PermissionEnum.EVENT_GET_COORD.getCode()),
                    cb.equal(permissionJoin.get("label"), PermissionEnum.EVENT_GET_COORD.getLabel()),
                    cb.equal(permissionJoin.get("description"), PermissionEnum.EVENT_GET_COORD.getDescription())
            );
            var isCurrentAudiencePermission = cb.and(
                    cb.isTrue(permissionJoin.get("systemManaged")),
                    cb.or(memberAudienceIsCurrent, coordinatorAudienceIsCurrent)
            );
            var hasAuthority = permissionJoin.get("code").in(userAuthorities);

            return cb.or(isPublic, cb.and(isCurrentAudiencePermission, hasAuthority));
        };
    }
}
