package br.org.gam.api.event.persistence;

import br.org.gam.api.event.domain.EventStatus;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.JoinType;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
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

    public static Specification<EventEntity> orderByEffectiveStatus(Sort sort, Instant evaluationInstant) {
        return (root, query, builder) -> {
            if (Long.class == query.getResultType() || long.class == query.getResultType()) {
                return null;
            }

            query.distinct(false);
            Expression<Integer> effectiveStatusOrder = builder.<Integer>selectCase()
                    .when(
                            builder.and(
                                    root.get("status").in(EventStatus.SCHEDULED, EventStatus.COMPLETED),
                                    builder.greaterThan(root.get("endDate"), evaluationInstant)
                            ),
                            EventStatus.SCHEDULED.ordinal()
                    )
                    .when(
                            builder.and(
                                    root.get("status").in(EventStatus.SCHEDULED, EventStatus.COMPLETED),
                                    builder.lessThanOrEqualTo(root.get("endDate"), evaluationInstant)
                            ),
                            EventStatus.COMPLETED.ordinal()
                    )
                    .when(builder.equal(root.get("status"), EventStatus.LOCKED), EventStatus.LOCKED.ordinal())
                    .when(builder.equal(root.get("status"), EventStatus.FINALIZED), EventStatus.FINALIZED.ordinal())
                    .otherwise(EventStatus.CANCELLED.ordinal());

            List<jakarta.persistence.criteria.Order> orders = new ArrayList<>();
            for (Sort.Order order : sort) {
                Expression<?> expression = "status".equals(order.getProperty())
                        ? effectiveStatusOrder
                        : root.get(order.getProperty());
                orders.add(order.isAscending() ? builder.asc(expression) : builder.desc(expression));
            }
            query.orderBy(orders);
            return null;
        };
    }
}
