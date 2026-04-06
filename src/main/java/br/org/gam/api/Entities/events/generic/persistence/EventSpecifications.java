package br.org.gam.api.Entities.events.generic.persistence;

import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class EventSpecifications {
    public static Specification<EventEntity> fetchLocation() {

        return (root, query, builder) -> {
            if (Long.class != query.getResultType()) {
                root.fetch("location", JoinType.LEFT);
            }
            return null;
        };
    }
}
