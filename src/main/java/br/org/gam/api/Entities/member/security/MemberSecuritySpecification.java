package br.org.gam.api.Entities.member.security;

import br.org.gam.api.Entities.member.MemberStatus;
import br.org.gam.api.Entities.member.persistence.MemberEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;

public class MemberSecuritySpecification {
    public static Specification<MemberEntity> canGetMember(Set<String> userAuthorities) {
        return (root, query, cb) -> {
            if (userAuthorities.contains("MEMBER_GET_NON_ACTIVE")) return cb.conjunction();

            return cb.equal(root.get("status"), MemberStatus.ACTIVE);
        };
    }
}
