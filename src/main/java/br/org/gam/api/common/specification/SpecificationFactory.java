package br.org.gam.api.common.specification;

import org.springframework.data.jpa.domain.Specification;
import java.util.Collection;

public final class SpecificationFactory {
    private SpecificationFactory() {}

    public static <T> Specification<T> equals(String field, Object value) {
        return (root, query, cb) -> cb.equal(root.get(field), value);
    }

    public static <T> Specification<T> like(String field, String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get(field)), "%" + value.toLowerCase() + "%");
    }

    public static <T, C extends Comparable<? super C>> Specification<T> isGreaterThanOrEqual(String field, C value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(field), value);
    }

    public static <T, C extends Comparable<? super C>> Specification<T> isLessThanOrEqual(String field, C value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(field), value);
    }

    public static <T> Specification<T> in(String field, Collection<?> values) {
        return (root, query, cb) -> root.get(field).in(values);
    }
}